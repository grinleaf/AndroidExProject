package com.grinleaf.ex003widget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //화면에 보여지는 View 들을 제어하는 참조변수를 가급적 멤버변수로 선언할 것! (java에서 멤버변수는 0에 해당하는 값을 가짐. 참조변수는 NULL값)
    TextView tv, tv2;
    Button btn, btn2;
    EditText et;

    //이 액티비티가 생성되면 자동으로 실행되는 콜백메소드
    //이 메소드 내에서 보여줄 View 들을 설정하는 작업과 초기화 작업을 함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //xml 에서 만들어진 View 객체(텍스트/이미지든 상관없이)를 찾아와서 참조변수에 대입
        //액티비티 클래스는 연결된 xml 문서에서 View 를 찾아주는 기능 메소듣 존재
        //tv= new TextView(this);
        //tv.setText("Hello");
        tv= this.findViewById(R.id.tv);
        btn= this.findViewById(R.id.btn);

        //버튼이 클릭되는 것을 듣는 리스너 객체 생성 및 추가 : 버튼 클릭 시 문자열이 set 되도록하는 기능
        //View.OnClickListener() 를 구현하는 클래스의 객체를 만들어 생성해야 사용 가능! --> 익명클래스 이용
        btn.setOnClickListener(new View.OnClickListener() {
            //해당 리스너객체가 감시하는 버튼이 클릭되면 자동으로 발동하는 [ 콜백메소드 ]
            @Override
            public void onClick(View view) {
                tv.setText("Nice to meet you.");
            }
        });

        //실습 1) btn2 View 를 클릭할 때 EditText 의 글씨를 읽어와 TextView2에 보여주기
        et= findViewById(R.id.et);
        btn2= findViewById(R.id.btn2);
        tv2= findViewById(R.id.tv2);

        //btn2 View 가 클릭되는 것을 듣는 리스너객체 생성 및 설정
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText 에게 쓰여있는 글씨를 가져오기(get)
                    //EditText 객체가 글씨를 바로 가지고 있는게 아니라, EditText 객체 내에 글씨를 가진 Editable 객체가 있는 것!
                    //Editable able= et.getText(); --> 그러므로 Editable 객체에 가져올 글씨를 대입받아야함
                    //String s= able.toString(); --> 스트링 객체에 able 객체가 가진 문자열 대입
                    String s= et.getText().toString(); //위의 두 줄을 한 줄로 축약!

                //가져온 글씨를 tv2 View 안에 설정하기(set)
                tv2.setText(s);
            }
        });

    }
}