package com.grinleaf.ex087retrofit2marketapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.grinleaf.ex087retrofit2marketapp.databinding.ActivityEditBinding;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditActivity extends AppCompatActivity {

    ActivityEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        //제목글씨 변경
        getSupportActionBar().setTitle("글쓰기");
        //제목줄 왼쪽의 <- 화살표 버튼(up button) 띄우기
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //홈버튼 자리에 업버튼 놓겠다

        binding.btnSelect.setOnClickListener(v->{
            Intent intent= new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            resultLauncher.launch(intent);
        });

        binding.btnSave.setOnClickListener(v->clickSave());
    }

    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()==RESULT_OK){
                Uri uri= result.getData().getData();    //intent.getData() 와 uri.getData()의 체인
                Glide.with(EditActivity.this).load(uri).into(binding.iv);
                
                //uri 는 콘텐츠 주소(DB 주소)이므로, 서버에 전송하기 위한 이미지파일의 [ 절대경로 ]가 필요함
                //uri --> 절대경로 변환하기 (절대경로 저장 멤버변수 선언, uri 를 절대경로로 바꿔서 리턴해주는 메소드 복붙-메모장참고)
                imgPath= getRealPathFromUri(uri);
                //확인
                new AlertDialog.Builder(EditActivity.this).setMessage(imgPath).create().show();
            }
        }
    });

    //이미지파일의 절대경로를 저장하는 멤버변수
    String imgPath;

    //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드 (메모장 참고)
    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return  result;
    }

    void clickSave(){
        //작성한 데이터들 서버에 업로드하기
        //전송할 데이터들 [ name, title, msg, price, imgPath ]
        String name= binding.etName.getText().toString();
        String title= binding.etTitle.getText().toString();
        String msg= binding.etMsg.getText().toString();
        String price= binding.etPrice.getText().toString();

        //Retrofit 작업 시작
        //Retrofit 객체 생성 --> MainActivity, EditActivity 두 쪽에 왔다갔다 하기 번거로우니, 객체 생성 클래스를 만들자! RetrofitHelper
        Retrofit retrofit= RetrofitHelper.getRetrofitInstanceScalars(); //실무에서는 오류 안내메시지도 json 형식으로 폼을 만들어두고 Gson 으로 받아들임 (요건 문자열 받아들이기 연습용!)
        RetrofitService retrofitService= retrofit.create(RetrofitService.class);
        
        //1) 이미지 파일을 MultiPartBody.Part 로 포장하기 : @Part 로 보냄 --> 사용자가 이미지를 첨부하지 않을 경우도 설정
        MultipartBody.Part filePart= null;
        if(imgPath!=null){
            File file= new File(imgPath);
            RequestBody requestBody= RequestBody.create(MediaType.parse("image/*"),file);   //file 객체를 첫번째 파라미터 형식(파싱)에 맞게 포장(패킹)하는 과정
            filePart= MultipartBody.Part.createFormData("img",file.getName(),requestBody); //서버(.php)에서 파일을 받았을 때($file= $_FILES['요기 부분']) 식별자
        }
        
        //2) 나머지 String 데이터들은 Map Collection 에 저장됨 : @PathMap
        Map<String, String> dataPart= new HashMap<>();
        dataPart.put("name",name);  //(식별자, 데이터) --> 요게 서버(.php)에서 데이터 받을 때 쓰이는 식별자!
        dataPart.put("title",title);
        dataPart.put("msg",msg);
        dataPart.put("price",price);

        Call<String> call= retrofitService.postDataToServer(dataPart,filePart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s= response.body();
                Toast.makeText(EditActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(EditActivity.this, "error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(EditActivity.this).setMessage("error:"+t.getMessage()).create().show();
            }
        });
    }

    //up 버튼(<-) 눌렀을 때 finish() 발동하는 콜백메소드
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home) finish();

        return super.onOptionsItemSelected(item);
    }

    //요것도 onOptionsItemSelected()와 동일한 기능을 주는 메소드임! AppCompatActivity 를 상속받은 MainActivity 내에서는 onNavigateUp()이 안먹혀용
//    @Override
//    public boolean onSupportNavigateUp() {
//        finish();
//        return super.onSupportNavigateUp();
//    }
}