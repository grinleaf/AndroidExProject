package com.grinleaf.ex079databinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.grinleaf.ex079databinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    
    //Data Binding 은 View Binding 의 업그레이드 버전 : 근데 성능차이는 유의미하게 있지는 않다고 함! 그냥 방식의 차이
    //Data Binding 을 하면, View Binding 이 자동으로 되기 때문에 '업그레이드' 라고 칭함
    //1. Data Binding 기능 on 해야 사용 가능 - build.gradle 문서

    //2. 뷰 바인딩과 다르게, xml 문서의 root(최상위 뷰)가 <layout> 이라는 태그로 되어있어야만 바인딩 클래스가 자동으로 만들어진다!

    //2-9. 앞의 과정 모두 거치고 나면 참조변수 사용가능
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //2-10. 화면에 보여줄 액티비티를 set 하는 구문
//        setContentView(R.layout.activity_main);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        //2-11. 뷰와 연결된 데이터를 가진 User 객체 생성 : xml 문서의 <data> 에서 설정한 옵저버블 변수를 MainActivity 에서 접근하여 설정 가능하다!
        User user= new User("sam",20);
        binding.setUser(user);

        //2-12. 버튼 작동

        //2-12-a. 요건 ViewBinding
        binding.btn.setOnClickListener(v-> binding.setUser(new User("robin",25)));
    }
}