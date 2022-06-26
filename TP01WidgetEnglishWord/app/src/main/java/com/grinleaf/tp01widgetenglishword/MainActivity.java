package com.grinleaf.tp01widgetenglishword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //1. 액티비티가 보여주는 뷰들의 참조변수 선언
    TextView tvQuestion, tvAnswer;
    EditText et;
    Button btn;

    //3. 문제로 출제될 영단어들을 가진 배열 선언
    String[] qs= new String[]{"APPLE","HOUSE","CAR","DOG","SKY"};
    //5-3. 한글 뜻 정답을 가진 배열 선언
    String[] as= new String[]{"사과","집","자동차","개","하늘"};

    //4-1. 출제된 문제번호 만들기
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2. xml 에서 만든 뷰들을 참조변수에 찾아와서 대입하기
        tvQuestion= findViewById(R.id.tv_question);
        et= findViewById(R.id.et);
        btn= findViewById(R.id.btn);
        tvAnswer= findViewById(R.id.tv_answer);

        //4-2. 랜덤함수 적용
        Random rnd= new Random();
        num= rnd.nextInt(qs.length);    //5 : 0~4 사이의 랜덤한 숫자 대입
        tvQuestion.setText(qs[num]);
        
        //5. 버튼 클릭 시 EditText 에 입력된 글씨를 가져오기 + 영단어의 한글 뜻인지 확인하여 결과창 설정
            //5-1. 버튼 클릭하는 것을 듣는 리스너 객체 생성 및 설정
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
            //5-2. EditText 에 입력된 글씨 가져오기
                    String s= et.getText().toString();
            //5-4. 가져온 글씨 = 영단어 뜻인지 확인
                    if(s.equals(as[num])){
                        //5-5. TRUE 의 결과를 TextView 에 설정
                        tvAnswer.setText("정답!");

                        et.setText("");
                    }else {
                        //5-6. FALSE 의 결과를 TextView 에 설정
                        tvAnswer.setText("오답!");
                    }

        //6. 다음 문제를 새롭게 제시
                    Random rnd= new Random();
                    num= rnd.nextInt(qs.length);    //5 : 0~4 사이의 랜덤한 숫자 대입
                    tvQuestion.setText(qs[num]);
                }
            });
    }
}