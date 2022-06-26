package com.grinleaf.ex095kotlinhello

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

//대략적인 코틀린 코딩 방식 살펴보기 [ JAVA -> Kotlin ]
//1. 클래스 상속 키워드 extends -> ":" + 클래스명 바로 뒤에 주생성자(Primary 생성자)를 호출하는 "()"가 필요
//2. @Override protected void onCreate() -> override fun onCreate() + 디폴트 접근 제한자가 public !
//3. 파라미터 표기 (자료형 메소드명) -> (메소드명 : 자료형 ? )
//4. 코틀린 언어로 작성한 코드도 java 언어와 동일하게 .class 로 컴파일됨 (서로 100% 호환 가능)
//5. 변수 선언 자료형 변수명 -> var 변수명:자료형 / var 변수명= find... 시 제네릭 안에 자료형을 기입해 추론가능하도록 작성하기도 함
//6. 문장의 끝을 나타내는 ";" -> ";"이 무시됨. 무의미한 코드 (에러는 아님!)
//7. 리스너 객체 생성 { v-> clickBtn() } -> it 키워드
//8. 함수 선언 void aaa(){} -> fun aaa():자료형{} 리턴타입 x

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        var btn:Button = findViewById(R.id.btn)
        btn.setOnClickListener {
            clickBtn()
        }
    }

    fun clickBtn(){
        var tv= findViewById<TextView>(R.id.tv)
        //tv.setText("Nice to meet you.")
        //코틀린에서는 set...() 메소드를 권장하지 않음
        //...부분을 멤버변수 쓰듯이 대입하는 코딩스타일 권장
        tv.text= "Nice to meet you"
    }

    override fun onResume() {
        super.onResume()
        //코틀린으로 개발 시, 편집기에서 Toast 자동완성 기능은 소문자 toast 쓰면 나옴
        Toast.makeText(this,"onResume Toast Message",Toast.LENGTH_SHORT).show()
    }
}