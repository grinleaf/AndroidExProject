package com.grinleaf.ex023listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    //Adapter View 참조변수 : 오로지 String 배열, Text 형태일 때만 사용가능. Adapter 클래스 상속 받음 [ ArrayAdapter 객체 ]
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= findViewById(R.id.listview);

        //리스트뷰 안의 항목 뷰들을 원하는 모양으로 설정하고자 할 경우, 직접 Adapter 객체를 만들어서 xml 로 레이아웃 모양을 설계 -> 전달해줘야 함!
        //Adapter 중에 String 배열을 TextView 로 만들어 전달해주는 기능을 가진 [ ArrayAdapter 객체 ]
        adapter= ArrayAdapter.createFromResource(this, R.array.datas, R.layout.list_item); //파라미터 : (context, 들어갈 String 배열 데이터, 적용할 layout 형태)
        listView.setAdapter(adapter);

        //리스트뷰의 항목(Item)을 클릭 시 반응하는 리스너 객체 생성 및 설정
            //setOnClickListener() 는 리스트뷰 자체가 클릭되었을 때. 리스트뷰 안의 각각의 '항목' 이 클릭될 때의 상황을 지칭해야함
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //세번째 파라미터 i : 원래 변수명은 position. 클릭된 item 의 위치번호를 의미(0부터 시작)
                //Toast.makeText(MainActivity.this, i+"", Toast.LENGTH_SHORT).show(); //파라미터 i 의 자료형이 int 이므로, String 형으로 형변환

                //해당 위치의 데이터값을 알고싶을 경우, 파라미터 i 활용
                //1. res/values 폴더 안의 arrays.xml (미리 작성-문서명 고정) 에 작성해둔 "datas" 라는 이름의 String 배열 객체 얻어오기

                //res 폴더 창고관리자(Resource) 객체 선언(소환)
                Resources res= getResources();
                //Resources 객체는 이미 res 폴더 창고관리자(운영체제)가 관리하고 있음. 이미 존재하기 때문에 new 하지않고 바로 사용
                //MainActivity.this.getResources(); 에서 앞부분이 생략되어 있는 구조
                    //MainActivity - Activity 상속 - Context(=Application) : 운영체제의 대리인 상속  //이 관계임을 항상 염두에 둘 것
                String[] datas= res.getStringArray(R.array.datas);
                
                Toast.makeText(MainActivity.this, datas[i], Toast.LENGTH_SHORT).show(); //파라미터 i를 datas 배열 객체의 요소 인덱스번호로 활용
            }
        });

        //item long click ( == 꾹 누르는 동작 ) 리스너 객체 생성 및 설정 ( != clickListener : 눌렀다 떼는 동작이 완료되었을 때 )
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(MainActivity.this, i+"번 롱클릭", Toast.LENGTH_SHORT).show();

                //이 리턴값이 false 이면, OnItemClick 도 함께 발동된다. 리턴값을 consume 이라고 지칭하고 있음.
                //즉, click event 를 onItemLongClick 에서 'consume(소비)' 완료 했는가 여부를 판단하는 문장
                return true;
            }
        });

    }
}