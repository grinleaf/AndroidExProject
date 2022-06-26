package com.grinleaf.ex078viewbinding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.grinleaf.ex078viewbinding.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    
    //0. findViewById() 메소드의 번거로움 + 성능 문제 해결한 안드로이드의 기능 : [ ViewBinding ]
    //라이브러리 X. 기본 프로젝트에 적용되어 있지 않음(off 상태) --> 기능 on 해줄 것! (build.gradle)

    //*** view binding 기술의 원리 ***
    //xml 레이아웃파일(activity_main.xml)의 뷰들을 이미 연결하고 있는 클래스가 자동으로 만들어져 있다.
    //해당 클래스의 이름은 xml 문서의 이름을 기반으로 만들어져 있음
    
    //1. activity_main.xml 의 뷰들을 참조하는 바인딩 객체의 참조변수 (VH 와 비슷한 기능)
    ActivityMainBinding binding;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //2. activity_main.xml 문서를 기반으로 뷰 객체들을 생성(inflate)하여 바인딩 객체 생성
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        //3. 액티비티에게 보여줄 내용물뷰(ContentView)를 binding 객체의 뷰로 설정
        setContentView(binding.getRoot());  //Root = 최상위 뷰 (LinearLayout)

        //4. 바인딩 객체 안에 있는 id 가 "tv"인 TextView 의 글씨 변경 + btn 버튼을 눌러 tv 의 글씨 변경
        //이미 바인딩이 되어있어 별도의 findViewById() 나 TextView 참조변수 필요없음 바인딩 객체 안에 id 를 기반으로 각 뷰와 연결된 참조변수들이 이미 만들어져 있음
        binding.tv.setText("Nice");  //xml 문서에서 지정한 id 가 곧 변수명이 됨!
        binding.btn.setOnClickListener(v->binding.tv.setText("nice to meet you."));

        //* 롱클릭 리스너 기존방식
//        binding.btn.setOnLongClickListener(new View.OnLongClickListener(){
//            @Override
//            public boolean onLongClick(View view) {
//                return true;
//            }
//        });

        //* 롱클릭 리스너 람다식 처리
        //binding.btn.setOnLongClickListener((view,a,b)->{});   //파라미터 변수가 여러개일 때
        binding.btn.setOnLongClickListener( view->{
            Toast.makeText(this, "long clicked", Toast.LENGTH_SHORT).show();
            return true;
        });

        binding.btn2.setOnClickListener(v->{
            binding.tv2.setText(binding.et.getText().toString());
            binding.et.setText("");
        });

        //6. 동적으로 MyFragment 객체를 붙이기 (activity_main.xml 에 fragment_my.xml 붙이기)
        MyFragment fragment= new MyFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }

    //8. 대량의 데이터 담는 ArrayList<>
    ArrayList<Item> items= new ArrayList<>();

    @Override
    protected void onResume() { //액티비티가 화면에 보여질 때
        super.onResume();

        //10. 대량의 데이터들 추가
        items.add(new Item(R.drawable.newyork,"뉴욕"));
        items.add(new Item(R.drawable.paris,"파리"));
        items.add(new Item(R.drawable.sydney,"시드니"));
        items.add(new Item(R.drawable.newyork,"뉴욕"));
        items.add(new Item(R.drawable.paris,"파리"));
        items.add(new Item(R.drawable.sydney,"시드니"));

        //11. 어댑터 객체 생성 및 리사이클러뷰에 연결
        adapter= new MyAdapter(this, items);
        binding.recyclerview.setAdapter(adapter);
    }
}