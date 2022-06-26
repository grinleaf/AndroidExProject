package com.grinleaf.ex099kakaologin

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        //kakao sdk initialize
        KakaoSdk.init(this, "1e579b171c946c2eca6c6cb8ee6d17fd")
    }
}