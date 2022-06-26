package com.grinleaf.ex096kotlinrecyclerviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ItemActivity : AppCompatActivity() {

    val iv:ImageView by lazy { findViewById(R.id.iv) }
    
    //val tv by lazy { findViewById(R.id.tv) as TextView }  //변수 자료형을 자동추론으로 처리하고 싶을 때,  1) as 형변환   2) <> 제네릭 문법
    val tv by lazy { findViewById<TextView>(R.id.tv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        //MainActivity 에서 넘어온 Intent 에게 데이터들 받기
        val title= intent.getStringExtra("title")
        val message= intent.getStringExtra("msg")
        val img= intent.getIntExtra("img",R.drawable.koala)

        //title 을 제목줄에 표시
        supportActionBar!!.title= title //nonnull !!

        tv.text= message
        iv.setImageResource(img)

        //iv 가 전환효과의 대상이 되도록 별칭 설정
        iv.transitionName= "imgAnimation"   //adapter 에서 설정했던 효과의 식별자를 설정

        //업버튼(<- 뒤로가기) 보이기
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)  //위에서 !! 을 썼으니 통일해줘야함~ ? 불가
    }

    //업버튼에 클릭 시 onBackPressed() 호출하여 뒤로가기 기능 주기
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}