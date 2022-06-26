package com.mrhi2022.ex033fragment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    MyFragment myFragment;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //프레그먼트를 제어하는 관리자 객체 소환
        fragmentManager= getSupportFragmentManager();

        btn= findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // id가 container인 녀석에게 MyFragment를 추가..

                myFragment= new MyFragment();

                //매니저에게 프레그먼트의 동적작업(추가/삭제/재배치)을 시작한다고 명령
                //트랜잭션 : 롤백기능이 있는 프로세스(작업) - 완료했다고 말하기 전에는 실제 작업이 실행되지 않음.
                FragmentTransaction tran= fragmentManager.beginTransaction();

                tran.add(R.id.container, myFragment);

                //만약 새로 추가되는 Fragment에 어떤 정도를 전달하고 싶다면.
                Bundle bundle= new Bundle();
                bundle.putString("name", "sam"); //key:식별자, value:값
                bundle.putInt("age", 20);        //key:식별자, value:값

                myFragment.setArguments(bundle);

                //만약 Fragment를 Back Stack에 저장하고 싶다면..
                tran.addToBackStack(null);

                tran.commit(); //트랜잭션 작업 완료!!



            }
        });

        Button btn2= findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 프레그먼트 관리자에게 프레그먼트 삭제 작업 진행
                FragmentTransaction tran= fragmentManager.beginTransaction();

                if(myFragment != null) tran.remove(myFragment);

                tran.commit(); //작업 완료

            }
        });

    }
}