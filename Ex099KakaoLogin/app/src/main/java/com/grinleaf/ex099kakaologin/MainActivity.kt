package com.grinleaf.ex099kakaologin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.grinleaf.ex099kakaologin.databinding.ActivityMainBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //카카오 키 해시 얻어오기 - kakao sdk 먼저 추가
        val keyHash:String= Utility.getKeyHash(this)
        Log.i("KEYHASH",keyHash)

        binding.btnKakaologin.setOnClickListener { clickLogin() }
        binding.btnLogout.setOnClickListener { clickLogout() }
        binding.btnUnlink.setOnClickListener { clickUnlink() }
    }

    private fun clickLogin(){
        //*** 함수의 파라미터에 함수를 담은 변수가 들어갈 때 사용 방법! ***
        //1) 로그인 결과 여부 callback 익명함수 (카카오톡 로그인 함수의 파라미터로 쓰기 위한 변수)
        val callback= fun(token:OAuthToken?, error:Throwable?){
            if(error!=null){
                Toast.makeText(this, "error: ${error.message}", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                loadUserInfo()
            }
        }
        //2) 변수 callback 을 람다식으로 축약하기
//        val callback:(OAuthToken?,Throwable?)->Unit= {
//            token, error ->
//        }

        //카카오 로그인이 가능한지 확인 --> 가이드 문서에 다 있음!
        if( UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
            //카카오톡 로그인(권장)
            UserApiClient.instance.loginWithKakaoTalk(this,callback= { token, error-> //3) 익명 함수로 바로 만들기
                if(error!=null){
                    Toast.makeText(this, "error: ${error.message}", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    loadUserInfo()
                }
            })
        }else{
            //카카오 계정 로그인
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback) //1)번 가져다 쓰기 ㅇㅅㅇ
        }

    }

    private fun loadUserInfo(){
        UserApiClient.instance.me { user, error ->
            if(error!=null) Toast.makeText(this, "사용자 정보 요청 실패", Toast.LENGTH_SHORT).show()
            else if(user!=null){
                val memberId:Long?= user.id                                 //회원번호 - 회원 식별자로 사용 가능
                val nickname= user.kakaoAccount?.profile?.nickname          //닉네임
                val email= user.kakaoAccount?.email                         //이메일
                val profileImg= user.kakaoAccount?.profile?.profileImageUrl //프로필 이미지

                binding.tvNickname.text= nickname
                binding.tvEmail.text= email
                Glide.with(this).load(profileImg).into(binding.civ)
            }
        }
    }
    
    private fun clickLogout(){
        //로그아웃만 하고, 서버와의 연결 token 은 유지
        UserApiClient.instance.logout { error ->
            if(error!=null) Toast.makeText(this, "로그아웃 실패", Toast.LENGTH_SHORT).show()    
            else{
                Toast.makeText(this, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
                binding.tvNickname.text= "닉네임"
                binding.tvEmail.text= "이메일"
                Glide.with(this).load(R.mipmap.ic_launcher).into(binding.civ)
            }
        }
    }
    
    private fun clickUnlink(){
        //로그아웃도 하고, 서버와의 연결도 종료 --> 재로그인 시 동의항목 다시 물어봄
        UserApiClient.instance.unlink { error ->
            if(error!=null) Toast.makeText(this, "연결끊기 실패", Toast.LENGTH_SHORT).show()
            else{
                Toast.makeText(this, "서버와의 연결이 종료되었습니다.", Toast.LENGTH_SHORT).show()
                binding.tvNickname.text= "닉네임"
                binding.tvEmail.text= "이메일"
                Glide.with(this).load(R.mipmap.ic_launcher).into(binding.civ)
            }
        }
    }
}