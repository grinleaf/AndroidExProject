package com.grinleaf.ex054admob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity {

    //모바일 광고 플랫폼 : AdMob (Google 전용) - https://admob.google.com/intl/ko/home/
    // 1) 구글계정으로 가입 및 로그인
    // 2) 앱 등록 및 광고 단위

    //AdMob SDK 외부라이브러리를 이 프로젝트에 적용하기(다운로드&연결) - 가이드 문서 참조 (https://developers.google.com/admob)
    //Gradle 빌드 프로그램이 라이브러리 적용 작업을 손쉽게 해준다. admob 의 경우 가이드에 '프로젝트 수준의 build.gradle 파일' 이라고 언급
    // --> 안드로이드 최신버전(범블비)부터 setting.gradle 에서 작업하는 것으로 변경됨 ! 주의할 것

    //1-1. 배너광고 참조변수
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //모바일 광고 SDK 초기화 - 가이드에서 제시한 초기화 방법 (외우지마라~!~!)
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                //광고 초기화가 완료되면 실행되는 영역
            }
        });

        //1-2. 배너 광고 뷰 참조
        adView= findViewById(R.id.adview);

        //1-3. 광고 로드하기
        AdRequest adRequest= new AdRequest.Builder().build();   //광고 요청 객체 AdRequest
        adView.loadAd(adRequest);

        //2. 전면광고 보여주기
        //2-1. 버튼 클릭시 전면광고 보여주기
        findViewById(R.id.btn).setOnClickListener(view -> {showInterstitialAd();});
        
        //3. 보상형광고 보여주기
        //3-1. 버튼 클릭시 보상형 광고 보여주기
        findViewById(R.id.btn02).setOnClickListener(v -> showRewardedAd());   //람다식 실행문이 한줄일 때 {};도 생략가능함

        //4. 네이티브광고 : 원하는 모양으로 광고모양 레이아웃 설계 가능 (가이드 문서 명확x. 수업보류)

    }//onCreate method

    //2-3. 전면광고 참조변수 - 전면광고 가이드 참조(https://developers.google.com/admob/android/interstitial?hl=ko)
    InterstitialAd mInterstitialAd;

    //2-2. 전면광고를 보여주는 기능 메소드
    void showInterstitialAd(){
        //sample Unit Id : ca-app-pub-3940256099942544/1033173712

        //2-4. 광고 요청 객체 만들기
        AdRequest adRequest= new AdRequest.Builder().build();
        
        //2-5. 전면광고 로드하기 - 마지막(4번째) 파라미터 callback = 리스너객체(콜백메소드 작성하기)
        InterstitialAd.load(this, "ca-app-pub-6969940641042735/2410215340", adRequest, new InterstitialAdLoadCallback() {
            //2-6. 광고로딩에 실패했을 때 발동하는 콜백 메소드
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Toast.makeText(MainActivity.this, "interstitial Ad failed to load", Toast.LENGTH_SHORT).show();
            }

            //2-7. 광고로딩에 성공했을 때 발동하는 콜백 메소드
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);

                //2-9. 전면광고 객체를 멤버변수에 대입해두면, 다른 버튼을 눌렀을 때도 광고보여주기 등의 작업이 가능해짐
                MainActivity.this.mInterstitialAd= interstitialAd;

                //2-8. 성공했을 때 파라미터로 전달된 전면광고 객체 사용하여 전면광고 보이기
                interstitialAd.show(MainActivity.this);

            }
        });
    }

    //3-2. 보상형광고 참조변수
    RewardedAd rewardedAd;

    //3-3. 보상형광고 보여주는 기능메소드
    void showRewardedAd(){
        //sample Unit Id : ca-app-pub-3940256099942544/5224354917
        //3-4. 광고 요청 객체 생성
        AdRequest adRequest= new AdRequest.Builder().build();

        //3-5. 보상형 광고 로딩하기 - 전면광고 기능메소드와 동일한 방식
        RewardedAd.load(this, "ca-app-pub-6969940641042735/7826193559", adRequest, new RewardedAdLoadCallback() {

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Toast.makeText(MainActivity.this, "failed to load", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
                //3-6. 멤버변수에 보상형 광고 객체 참조시키기
                MainActivity.this.rewardedAd= rewardedAd;
                //3-7. 보상형 광고 보기 - 전면광고와 다른 부분 : 파라미터 두번째(클릭 시 유저가 일정시간 광고를 보아 보상(이득)을 얻었는지 확인하는 리스너 객체)
                rewardedAd.show(MainActivity.this, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        //3-8. 파라미터로 온 rewardItem(기준 시간 이상 광고 시청시 주어지는 보상 아이템 변수=개발자가 광고단위 ID 를 만들 때 설정한 값)
                        //3-9. rewardItem 의 상품명/수량 가져오기 - 샘플 ID 로 실행 시 type:"coins" / amount:10
                        String type= rewardItem.getType();  //상품명
                        int amount= rewardItem.getAmount();  //수량

                        Toast.makeText(MainActivity.this, type+" : "+amount, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}//MainActivity class