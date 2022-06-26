package com.grinleaf.ex086retrofit2imageupload;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.grinleaf.ex086retrofit2imageupload.databinding.ActivityMainBinding;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //3. 버튼에 대한 리스너 객체 생성 및 설정
        binding.btnSelect.setOnClickListener(v->clickSelect());
        binding.btnUpload.setOnClickListener(v->clickUpload());


        //1. 외부저장소 사용에 대한 동적퍼미션 + 퍼미션을 허가받은 상태인지 여부 확인(받지 않았을 경우 다이얼로그를 띄우는 requestPermissions();
        String[] permissions= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(checkSelfPermission(permissions[0])== PackageManager.PERMISSION_DENIED)
            requestPermissions(permissions,0);
    }

    //2. 퍼미션 허가 여부에 따른 토스트메시지 띄우기
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "외부 저장소 허용", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "이미지파일의 업로드가 불가능합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    //4. 버튼 기능 메소드 정의
    void clickSelect(){
        //4-1. 사진앱, 갤러리앱 실행하여 업로드할 사진 선택 : 화면을 바꿀 때(Intent), 실행할 앱의 이름을 모를 때(묵시적 Intent)
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");  //MIME 타입
//      startActivity(intent);  //4-1-a. 요렇게만 쓰면 결과를 받아서 돌아올 수가 없고, 화면만 바뀜!
        resultLauncher.launch(intent);


//        startActivityForResult(); //4-1-b. 전환한 화면에서 결과를 받아서 다시 돌아오기
    }

    //4-1-c. 4-1-b 를 사용하면 함께 써야할 콜백메소드(전환된 화면에서 결과를 받을 때의 동작 설정)
    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()==RESULT_OK){  //사진앱에서 사진 선택했을 경우
                //선택된 이미지의 URI 를 얻어오기
//                Intent intent= result.getData();
//                Uri uri= intent.getData();
                Uri uri= result.getData().getData();    //보통 요렇게 줄여씀!
                Glide.with(MainActivity.this).load(uri).into(binding.iv);

                //uri 는 실제 파일의 경로주소가 아니라 안드로이드에서 사용하는 Resource(자원)의 DB 주소. (=콘텐츠 주소)
                new AlertDialog.Builder(MainActivity.this).setMessage(uri.toString()).create().show();
                //다이얼로그에 content://com.google.android.apps.phtos.contentprovider/.../1195672902(이 마지막번호가 DB 식별번호) 요런 DB 주소가 뜸!
                //4-2. 실 주소가 아니기 때문에 서버에 보내면 인식을 못함. 실제 물리적인 파일 경로를 가져오는 과정이 필요!
                //uri 를 통해 절대주소(String) 로 변환하기 --> 여기서 [ 외부 저장소에 대한 동적 퍼미션 ] 필요! (위 과정까진 실주소가 아니라 사실 퍼미션이 필요 없었대 ㄷㄷ)

                //4-2-b. uri --> 절대주소(String) 으로 변환
                imgPath= getRealPathFromUri(uri);   //imgPath 는 멤버변수로 선언 --> 여기다 실제 주소를 받아와 대입함
                new AlertDialog.Builder(MainActivity.this).setMessage(imgPath).create().show();

            }else{
                //사진앱에서 사진선택을 안하고 돌아왔을 경우
            }
        }
    });

    String imgPath;

    //4-2-a. ** 메모장 자료 복붙(실무에서도 이렇게 한댕) : Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드
    String getRealPathFromUri(Uri uri){
        
        String[] proj= {MediaStore.Images.Media.DATA};
        //MediaStore.Images.Media.DATA == "SELECT no, name FROM board" --> 요기서 {no, name ...} 이부분 데이터 가져오는 것 : DB 의 컬럼명 !
        
        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
        //uri == FROM board, proj == {no, name...} 등 컬럼명(String) 데이터가 나열된 배열
        //selectionArgs, sortOrder(오름차순, 내림차순 설정) 등은 필요할때만 사용함
        Cursor cursor= loader.loadInBackground();   //커서 객체 생성
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);   //커서에게 컬럼명이 있는 위치(인덱스)를 찾아오도록 하는 메소드
        cursor.moveToFirst();   //커서의 동작
        String result= cursor.getString(column_index);  //인덱스를 스트링으로 가져오는 메소드
        cursor.close();
        return  result; //실제주소값(=인덱스) 반환
    }

    void clickUpload(){
        //4-3. Retrofit 라이브러리를 이용하여 이미지 업로드하기
        //4-3-a. Retrofit 객체 생성 : 서버로부터 echo 결과를 String 으로 돌려받기 위함
        Retrofit.Builder builder= new Retrofit.Builder();
        builder.baseUrl("http://grinleaf.dothome.co.kr");
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit= builder.build();

        //4-3-b. Retrofit 동작에 대한 인터페이스 설계 및 추상메소드 정의
        RetrofitService retrofitService= retrofit.create(RetrofitService.class);

        //4-3-d. File 을 MultiPartBody.Part 로 패킷화(포장) - 그 후 업로드해줘야함!
        File file= new File(imgPath);
        RequestBody requestBody= RequestBody.create(MediaType.parse("image/*"),file);   //요청 Body : 이미지파일 내용물
        MultipartBody.Part part= MultipartBody.Part.createFormData("img",file.getName(),requestBody);   //(식별자, 파일명, 요청 Body)

        //4-3-e. Call 객체 생성
        Call<String> call= retrofitService.uploadImage(part);

        //4-3-f. 네트워크 작업 = 통신 시작
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s= response.body();
                new AlertDialog.Builder(MainActivity.this).setMessage(s).create().show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}