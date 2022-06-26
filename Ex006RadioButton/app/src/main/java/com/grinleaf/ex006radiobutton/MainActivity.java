package com.grinleaf.ex006radiobutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    RadioGroup rg;
    Button btn;
    TextView tv;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //참조변수에 뷰객체 대입
        rg= findViewById(R.id.rg);
        btn= findViewById(R.id.btn);
        tv= findViewById(R.id.tv);
        ratingBar= findViewById(R.id.rating);

        //버튼 클릭에 반응하는 리스너 객체 생성 및 설정
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //RadioGroup 에게 체크된 RadioButton 이 무엇인지 가져오기(식별자를 줌 ㅠ)
                int id= rg.getCheckedRadioButtonId();

                //id 변수를 이용한 해당 RadioButton 객체를 찾아와 참조
                RadioButton rb= findViewById(id);
                tv.setText(rb.getText().toString());
            }
        });
        
        //RadioButton 의 체크상태가 변경될 때마다 반응하려면, 리스너를 일일이 설정해야함
        //RadioGroup 에 변경 리스너를 새로이 생성하여 설정해주는 방법이 권장됨
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //두 번째 파라미터 int i : 체크된 라디오버튼의 id 전달

                RadioButton rb= findViewById(i);
                tv.setText(rb.getText().toString());
                
            }
        });

        //레이팅값이 변경되는 것에 반응하는 리스너 객체 생성 및 설정
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                //두 번째 파라미터 v : 선택된 rating 값 소수점까지 (별점)
                //세 번째 파라미터 b : 사용자가 터치를 이용하여 점수를 변경했는지 여부를 파악

                tv.setText(v+"점");
            }
        });
        
    }
}