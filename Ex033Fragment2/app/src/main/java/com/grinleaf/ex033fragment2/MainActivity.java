package com.grinleaf.ex033fragment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    MyFragment myFragment;

    Button btn, btn2;

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
                //버튼을 누르면 id 가 container (FrameLayout) 인 뷰에 MyFragment 를 추가시키기
                myFragment= new MyFragment();

                //매니저에게 프레그먼트의 동적작업(추가/삭제/재배치)을 시작한다고 명령
                //트랜잭션 : 롤백 기능이 있는 프로세스(작업) - 완료했다고 말하기 전에는 실제 작업이 실행되지 않음
                FragmentTransaction tran= fragmentManager.beginTransaction(); //트랜잭션 시작 지점

                tran.add(R.id.container, myFragment);   //파라미터(프래그먼트작업(트랜잭션) 결과를 붙일 레이아웃(container) , 트랜잭션 대상이 되는 프래그먼트)

                //만약 새로 추가되는 Fragment 에 어떤 정보를 전달하고 싶을 경우
                Bundle bundle= new Bundle();    //보따리!
                bundle.putString("name","sam"); //파라미터 (식별자, 실제 값)
                bundle.putInt("age",20);

                myFragment.setArguments(bundle);    //필요한 데이터를 bundle 에 넣어서 프래그먼트에 set 하기 --> MyFragment 문서에서 bundle 을 받아 set

                //만약 Fragment 를 Back Stack 에 저장하고 싶을 경우 : Fragment add() 할 때마다 뒷 쪽에 깔리는 페이지들을 Back Stack 이라고 부름~
                tran.addToBackStack(null);  //요걸 넣으면 뒤로가기 눌렀을 때 Activity 자체가 꺼지는 것이 아닌, Back Stack 에 쌓인 페이지가 한장씩 롤백됨
                    //홈페이지에서 내정보(마이페이지) 탭을 눌렀을 때, 로그인이 안되어 있을 경우 로그인 프래그먼트를 띄웠다가(BackStack 저장), 로그인 되면 백스택 제거 하는 방식으로 활용하곤 한당
                
                tran.commit();  //트랜잭션 종료 지점 - 시작~종료 지점 사이에 작업을 코딩하면 됨!
            }
       });

        btn2= findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FragmentManager 에게 container 에서 Fragment 를 삭제하라고 명령하기
                FragmentTransaction tran= fragmentManager.beginTransaction();   //트랜잭션 시작

                if(myFragment !=null) tran.remove(myFragment);  //트랜잭션 내용

                tran.commit();  //트랜잭션 종료
            }
        });
    }
}