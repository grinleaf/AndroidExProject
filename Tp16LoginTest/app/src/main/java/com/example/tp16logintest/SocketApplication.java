package com.example.tp16logintest;

import android.app.Application;
import android.content.Context;

import com.kakao.sdk.common.KakaoSdk;

public class SocketApplication extends Application {

    Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=this;
        KakaoSdk.init(this,getString(R.string.kakao_app_key));
    }

    //    companion object {
//        var appContext : Context? = null
//    }
//    override fun onCreate() {
//        super.onCreate()
//        appContext = this
//        KakaoSdk.init(this,getString(R.string.kakao_app_key))
//    }
}
