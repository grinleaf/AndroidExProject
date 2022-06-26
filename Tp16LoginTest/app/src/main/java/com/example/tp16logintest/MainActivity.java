package com.example.tp16logintest;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.tp16logintest.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.user.UserApiClient;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLoginSignup.setOnClickListener(v-> clickSignUp());
        binding.btnLoginNaver.setOnClickListener(v-> clickNaver());
        binding.btnLoginKakao.setOnClickListener(v-> clickKakao());
        binding.btnLoginGoogle.setOnClickListener(v-> clickGoogle());
    }

    void clickSignUp(){

    }

    void clickNaver(){
        //네이버로그인 객체 초기화
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                this
                ,getString(R.string.naver_client_id)
                ,getString(R.string.naver_client_secret)
                ,getString(R.string.app_name)
        );

        //로그인 완료 or 취소
        private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {
                    String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                    String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                    long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                    String tokenType = mOAuthLoginModule.getTokenType(mContext);
                    new RequestApiTask(mContext, mOAuthLoginModule).execute();
                } else {
                    String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                    String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                    Toast.makeText(mContext, "errorCode:" + errorCode
                            + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    OAuthLogin mOAuthLoginModule;


    void clickKakao(){
        //로그인 콜백
        UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(tag, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(tag, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
            }
            return null;
        });

        UserApiClient.getInstance().me((user, meError)->{
            if (meError != null) {
                Log.e(tag, "사용자 정보 요청 실패", meError);
            } else {
                Log.i(tag, "사용자 정보 요청 성공" +
                        "\n회원번호: "+user.getId()+
                        "\n이메일: "+user.getKakaoAccount().getEmail());
            }
            return null;
        });
    }
    String tag;

    void clickGoogle(){
        GoogleSignInOptions signInOptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("698289336910-sr79k9qetnaafmf0uphigmh67bjd0mbc.apps.googleusercontent.com")
                .requestEmail()
                .build();

        Intent intent= GoogleSignIn.getClient(this,signInOptions).getSignInIntent();
        resultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent intent= result.getData();
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(intent);

            GoogleSignInAccount account= task.getResult();
            String email= account.getEmail();
            binding.tvResult.setText("구글 계정 email : "+email);
        }
    });


}