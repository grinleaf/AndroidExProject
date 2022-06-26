package com.grinleaf.ex004imageview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //1. 화면에 보여지는 View 들의 참조변수 선언
    ImageView iv;
    Button btnAus, btnBel, btnCan, btnKor, btnPrev, btnNext;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2. xml 문서에서 만든 View 객체들을 참조변수에 대입
        iv= findViewById(R.id.iv);
        btnAus= findViewById(R.id.btn_aus);
        btnBel= findViewById(R.id.btn_bel);
        btnCan= findViewById(R.id.btn_can);
        btnKor= findViewById(R.id.btn_kor);

        //3. 각각의 버튼 클릭 이벤트 발생 시 이미지 변경
        btnAus.setOnClickListener(listener);
        btnBel.setOnClickListener(listener);
        btnCan.setOnClickListener(listener);
        btnKor.setOnClickListener(listener);

        //4-B. ImageView 도 클릭 이벤트 발생 시 반응하는 리스너 설정 가능!
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*
                iv.setImageResource(R.drawable.flag_australia+num);
                //***R.drawable 폴더는 이름 순 정렬, 정렬순으로 가상의 식별키가 부여됨! 즉, ++할 때마다 다음 그림이 set 된다!
                num++;
                //단, 본인이 준비한 그림까지만 돌고 멈추도록 코드 작성해줄 것
                if(num>12) num=0;
              */
                //클릭 이벤트 발생 시 랜덤으로 이미지 set
                Random rnd= new Random(13);
                int num= rnd.nextInt();
                iv.setImageResource(R.drawable.flag_australia+num);

            }
        });



    }//onCreate method
    
    //4-A. 버튼 클릭을 듣는 리스너 객체 생성 + 참조변수(=멤버변수) 만들기
        //***멤버변수의 생성 시기는 객체가 생성될 때! 메소드 사용은 객체 생성 후 이므로 코드 위치가 뒤에 있더라도 상관없음 주의!
    View.OnClickListener listener= new View.OnClickListener() {
        //해당 리스너 객체가 감시하는 버튼 클릭 시 자동으로 발동하는 [ 콜백메소드 ]
        @Override
        public void onClick(View view) {    //감시하는 4 개의 버튼에게 클릭 이벤트 발생 시 하나의 객체가 전부 반응함
            //요것도 메소드라서 파라미터가 있넹!!!!!!!!!! --> view 가 클릭 이벤트가 발생한 Button 객체(View)를 참조하고 있음
            //5-A. 파라미터로 들어온 view 의 값(객체)이 어떤 객체인지 확인하여 각 객체마다 동작하게 만들기
            //*** xml 문서에서 설정했던 식별자 id는 사실 int 자료형으로 저장되어있음 ***
            int id= view.getId();
            switch(id){
                case R.id.btn_aus:
                    iv.setImageResource(R.drawable.flag_australia);
                    break;
                case R.id.btn_bel:
                    iv.setImageResource(R.drawable.flag_belgium);
                    break;
                case R.id.btn_can:
                    iv.setImageResource(R.drawable.flag_canada);
                    break;
                case R.id.btn_kor:
                    iv.setImageResource(R.drawable.flag_korea);
                    break;
            }
        }
    };

}//MainActivity class