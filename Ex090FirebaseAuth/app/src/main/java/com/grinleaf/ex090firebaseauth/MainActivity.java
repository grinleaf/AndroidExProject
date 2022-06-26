package com.grinleaf.ex090firebaseauth;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.grinleaf.ex090firebaseauth.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    
    //Firebase Authentication (인증) - 로그인 기능 구현
    //1. 이메일 및 비밀번호 기반 인증 : 이메일로 인증 확인을 통한 사용자 인증 (자동으로 인증 확인 메일이 발송됨)
    //2. ID 공급업체 사용 : Google, Apple, Facebook, Twitter, Github 계정 로그인 지원
    
    //Firebase 와 연동 - 콘솔 --> 메모장 필기 참고 (10)

    ActivityMainBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth= FirebaseAuth.getInstance();

        binding.btnSignup.setOnClickListener(v->clickSignUp());
        binding.btnSignin.setOnClickListener(v->clickSignIn());
        binding.btnGoogle.setOnClickListener(v->clickGoogle());
        binding.btnLogout.setOnClickListener(v->clickLogout());
    }

    void clickLogout(){     //로그아웃은 정말정말 쉬워용
        firebaseAuth.signOut();
        Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
    }

    void clickSignIn(){
        //이메일과 비밀번호를 이용한 로그인 인증
        String email= binding.etEmail.getText().toString();
        String pw= binding.etPw.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email,pw).addOnCompleteListener(task->{
            if(task.isSuccessful()){
                //인증받은 사용자인지 확인해야함
                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                    Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                    //현재 사용자의 정보를 가져옴 (password 는 가져올 수 없음!)
                    String mail= firebaseAuth.getCurrentUser().getEmail();
                    binding.tv.setText("사용자이메일 : "+mail+"\n");
                }else{
                    Toast.makeText(this, "이메일 인증을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "로그인 실패!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void clickGoogle(){
        //Google 계정을 이용한 간편로그인 기능 : 구글로그인은 고유의 화면 존재 - 이를 실행시키는 Intent 를 통한 startActivityForResult() 방식
        //단, Google 계정 로그인 SDK (라이브러리)를 별도로 추가해야함! : [ play-services-auth ]
        //이 앱에서 다른 앱의 기능을 연동할 때 AndroidManifest.xml 에 공개 패키지를 설정해줘야함!

        //구글 로그인 옵션객체 생성 - Builder
        GoogleSignInOptions signInOptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("108910960768-av265ahpdf17bg86tcsjptuhjnff2guk.apps.googleusercontent.com")
                .requestEmail()
                .build();
        //파라미터 : .Builder(구글 기본계정과 게임계정중 택 1)
        //파라미터 : .requestIdToken(구글 콘솔 - API 키 발급탭(사용자 인증정보)-프로젝트 선택-OAuth 2.0 클라이언트 ID 복붙)

        //구글 로그인 액티비티를 실행하는 Intent 객체 얻어오기
        Intent intent= GoogleSignIn.getClient(this,signInOptions).getSignInIntent();
        resultLauncher.launch(intent);
    }
    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            //로그인 결과를 가져온 인텐츠 객체 소환
            Intent intent= result.getData();
            //Intent 로부터 구글 계정 정보를 가져오는 작업 객체 생성
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(intent);

            GoogleSignInAccount account= task.getResult();
            String email= account.getEmail();
            binding.tv2.setText("구글 계정 email : "+email);
        }
    });

    void clickSignUp(){
        //이메일 및 비밀번호 인증 방식의 회원가입 : 입력된 이메일로 [인증 확인] 메일 전송, 사용자가 인증 확인 시 가입 완료되는 방식

        String email= binding.etEmail.getText().toString();
        String pw= binding.etPw.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //이메일과 비번을 받아 유저를 만드는 작업이 완료되었을 때 동작

                //task.isSuccessful() : 입력된 이메일과 패스워드가 사용 가능한지 여부를 검사한 결과 (=유효성 검사)
                //1) 이메일 형식에 맞는지
                //2) 패스워드가 6자리 이상인지
                //3) 기존 이메일에 같은 이름이 있는지
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "입력된 이메일과 비밀번호가 사용가능합니다.", Toast.LENGTH_SHORT).show();
                    //여기까지 firebase 회원 등록상태. but, 인증이 되어있지 않음
                    
                    //입력된 이메일로 인증확인 메일 전송 및 전송성공여부 확인
                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){    //여기서 task.isSuccessful() 은 이메일 인증 확인 여부
                                Toast.makeText(MainActivity.this, "전송된 이메일을 확인하시고 인증하세요.", Toast.LENGTH_SHORT).show();
                            }else{

                            }
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "이메일과 비밀번호 형식을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}