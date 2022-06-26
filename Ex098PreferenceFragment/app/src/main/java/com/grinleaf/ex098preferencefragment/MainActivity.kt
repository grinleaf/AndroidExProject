package com.grinleaf.ex098preferencefragment

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        //SettingFragment 를 동적 추가
        supportFragmentManager.beginTransaction().replace(R.id.container_fragment, SettingFragment()).commit()
        //replace() 는 아무 fragment 도 생성되지 않은 최초 1회에는 add 기능을 함
        //원래 직접 설정화면을 구성하면, 앱이 종료되어도 설정값을 유지할 수 있도록 SharedPreference 클래스를 사용해야하지만, PreferenceFragmentCompat 이 자동으로 저장해줌!
        //** 설정값 경로 - 디바이스 파일 - data/data/패키지명/shared_prefs/xml 문서 --> 해당문서 실행 시 setting.xml 에서 지정한 key 값을 name 으로 가진 설정값이 저장되어있음!
        //** 저장된 값을 가져다 쓰기 : 보통은 IntroActivity 에서 작업하는 편
        loadPreference()

    }
    //SharedPreference 에 저장된 설정값을 읽어오는 함수
    private fun loadPreference(){
        val pref:SharedPreferences= PreferenceManager.getDefaultSharedPreferences(this)
        //원래는 getSharedPreference(키값, 모드)로 일일이 가져와야하지만, 매니저가 대신 처리해줌
        var isMessage:Boolean= pref.getBoolean("message",false)   //만약, 한번도 체크한 적이 없을 경우 설정값 상태가 생성되어있지 않으므로, 파라미터에 디폴트값 함께 설정
        var isVibrate:Boolean= pref.getBoolean("vibration",false)

        Toast.makeText(this, "sound message : $isMessage \n vibration message : $isVibrate", Toast.LENGTH_SHORT).show()
    }
}