package com.grinleaf.ex091firebasechatting;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.grinleaf.ex091firebasechatting.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    
    //0. Firebase 연동 = 서버 준비
    ActivityMainBinding binding;
    Boolean isFirst= true;  //7-1. 처음 앱을 실행하여 프로필정보가 없는지 여부
    Boolean isChanged= false;   //8-1. 기존 프로필 이미지를 변경했는지 여부

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.civ.setOnClickListener(v->clickImage());
        binding.btn.setOnClickListener(v->clickBtn());
        
        //6. SharedPreference 에 저장되어있는 닉네임과 URL 이 있는지 확인하기 (앱을 재실행했을 때 로그인 데이터가 남아있어 본인 profileUrl 과 nickname 이 뜨도록)
        SharedPreferences pref= getSharedPreferences("account",MODE_PRIVATE);
        G.nickname= pref.getString("nickname",null);        //editor 에 put 할 때 입력한 식별자에 해당하는 문자열 get 하여 불러오기
        G.profileUrl= pref.getString("profileUrl",null);    //파라미터 (put 할 때 입력한 식별자, 식별자에 맞는 데이터가 없을 경우 반환할 값)

        if(G.nickname!=null){
            binding.et.setText(G.nickname);
            Glide.with(this).load(G.profileUrl).into(binding.civ);

            //7-2. 앱 실행 처음이 아닐경우
            isFirst= false;
        }
    }

    Uri imgUri; //3. 선택된 이미지의 uri

    void clickImage(){
        //2. 사진앱 실행하여 이미지 선택 후 결과 받기
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");  //intent 가 받아올 형식 지정 (=데이터 넣을 거푸집을 미리 정해서 보내는 거! 해당 형식 취급하는 앱들 전부 불러옴)
        resultLauncher.launch(intent);
        startActivityForResult(intent,0);
    }

    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()!=RESULT_OK) return;

            imgUri= result.getData().getData(); //get intent -> intent 가 가지고 온 데이터 get
            Glide.with(MainActivity.this).load(imgUri).into(binding.civ);

            //8-2. 새로운 사진 선택하여 프로필 변경 시, 프로필 사진 변경 여부를 true
            isChanged= true;
        }
    });

    void clickBtn(){
        //7. 로그인한 적이 없으면 저장 후 화면전환 / 로그인한 적이 있으면 그냥 화면전환 --> if 문, isFirst 선언
        //8. 이미지를 변경했다면 저장 후 화면전환 / 이미지를 변경하지 않았다면 그냥 화면전환 --> if 문 조건 추가, isChanged 선언
        if(isFirst||isChanged) {
            //4. 입장버튼 클릭 시 선택된 civ 이미지 (profile image) + 입력된 text (nickname) 를 서버 (firestore database) 에 업로드
            //단, image 파일은 storage 에 먼저 업로드 할 것 (업로드 시간이 더 오래 걸림)
            saveData(); //image 파일 (storage) -> 이미지 경로+텍스트 (database) 업로드하는 메소드
        }else{
        //1. ChattingActivity 로 이동
            Intent intent= new Intent(this, ChattingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    void saveData(){
        //5. firebase storage 에 선택한 이미지파일 업로드
        if(imgUri==null){
            Toast.makeText(this, "프로필 이미지를 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        //5-1. storage 관리 객체 소환
        FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();

        //5-2. 저장될 파일명이 중복되지 않도록 날짜를 이용한 파일명 설정
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddhhmmss");
        String fileName= sdf.format(new Date())+".png";
        
        //5-3. firebase storage 에 이미지 업로드를 위한 참조객체 얻어오기 ★★ --> storage 에 이미지 업로드 후 / 다시 DB 에 올릴 이미지를 얻어오는 순서가 중요!
        StorageReference imgRef= firebaseStorage.getReference("profileImage/"+fileName);
        imgRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() { //이미지 경로를 넣고 성공했는지 듣는 리스너 객체 생성
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //5-3-a. 업로드 성공
                //5-3-b. Glide 는 이미지 로드 작업 시 이미지의 절대경로를 필요로 함 --> 다시 가져올 때 파일의 다운로드 주소가 필요함! (서버에 업로드 된 이미지의 URL 주소)
                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //5-3-c. firebase 저장소에 저장되어있는 이미지에 대한 다운로드 주소를 문자열로 얻어오기
                        String profileUrl= uri.toString();
                        Toast.makeText(MainActivity.this, "프로필 이미지 저장 완료", Toast.LENGTH_SHORT).show();
                        
                        //5-3-d. firebase database = firestore 에 닉네임, 이미지 URL 을 저장하기
                        //실제 앱에서는 내 이미지 URL 과 닉네임이 여러 액티비티에서 동일하게 쓰일 일이 많고, 그 때마다 Intent 로 Extradata put 해서 보내주기는 번거로움
                        //내 이미지 URL, 닉네임을 액티비티에 속하지 않고, 좀 더 상위 개념의 멤버변수로 두면 여러 액티비티에서 사용이 가능
                        //--> manifest.xml 에서 <activity> 들을 감싸는 <application> 의 멤버변수로 둘 수 있당!
                            //Application 능력을 준 java 문서(MyApplication.java)에서 MyApplication 에 Application 을 상속
                            //--> manifest.xml 에서 <application> 에 name 값을 ".MyApplication" 으로 주면, MyApplication.java 에 선언한 변수들을 상위 멤버변수로 사용할 수 있음!
                            //--> 번거로운 편이라 실무에서는 잘 안쓴당. 다른 방법을 사용! --> G (Global) 클래스 생성 [ = 전역변수 역할 클래스 ]

                        //firebase 에 있는 이미지 URL 과 닉네임을 문자열로 저장
                        G.profileUrl= uri.toString();
                        G.nickname= binding.et.getText().toString();

                        //5-3-e.
                        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
                        //profile 이라는 이름의 Collection 참조
                        CollectionReference profileRef= firebaseFirestore.collection("profile");

                        //닉네임을 Document 명으로 하고, 필드 값으로 이미지경로 URL 을 저장하기
                        //필드값은 Map Collection 으로 저장
                        HashMap<String, Object> profile= new HashMap<>();
                        profile.put("profileUrl",G.profileUrl);

                        profileRef.document(G.nickname).set(profile);

                        //5-4. 이 디바이스의 sharedPreference 에도 저장하기 : 로그인 정보를 저장해두고 다음 실행 때 간편하게 로드하기 위함 ★★
                        SharedPreferences pref= getSharedPreferences("account",MODE_PRIVATE);
                        SharedPreferences.Editor editor= pref.edit();    //edit() 가 SharedPreferences.Editor 객체를 리턴해줌

                        editor.putString("nickname", G.nickname);
                        editor.putString("profileUrl", G.profileUrl);

                        //5-4-a. 반드시 작성 완료되었음을 확인해야함
                        editor.commit();

                        //5-5. 저장이 완료되었으니 채팅화면으로 전환하기
                        Intent intent= new Intent(MainActivity.this,ChattingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {   //android 31 버전부터 뒤로가기로 MainActivity 가 안꺼지는 특성. 해당 콜백메소드 줘야 모든 버전에서 꺼짐!
        super.onBackPressed();
        finish();
    }
}