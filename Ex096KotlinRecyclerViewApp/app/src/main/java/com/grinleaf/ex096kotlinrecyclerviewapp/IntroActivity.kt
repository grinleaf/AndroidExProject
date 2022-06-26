package com.grinleaf.ex096kotlinrecyclerviewapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class IntroActivity : AppCompatActivity() {

    //멤버변수 : 반드시 초기화되어야함
    var btn:Button?= null       //nullable 변수
    lateinit var btn2:Button    //늦은 초기화 : null 을 넣지 않기 위한 목적이므로, null 대입 시 error

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        
        btn= findViewById(R.id.btn)
        //1. 익명클래스로 리스너 처리하는 버튼 : nullable 변수는 사용 시 반드시 null safety 연산자를 써줘야함(Button 이면 onclick 실행 / null 이면 onclick 실행 x)
        btn?.setOnClickListener(object :View.OnClickListener{    //object = 이름없는 OnClickListener 객체
            override fun onClick(p0: View?) {
                //MainActivity 실행
                //Intent intent= new Intent(IntroActivity.this, MainActivity.class);    //기존 자바의 화면전환 문법
                val intent:Intent= Intent(this@IntroActivity,MainActivity::class.java)
                startActivity(intent)
            }
        })

        btn2= findViewById(R.id.btn2)
        //2. SAM 변환으로 클릭 이벤트를 처리하는 버튼 : 익명클래스보다 표기법이 간소함 (람다식과 유사)
        btn2.setOnClickListener {
            //it == onClick() 의 파라미터 역할
            finish()
        }
    }
}