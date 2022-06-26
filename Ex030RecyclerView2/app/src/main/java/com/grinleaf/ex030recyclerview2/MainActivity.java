package com.grinleaf.ex030recyclerview2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //대량의 데이터
    ArrayList<Item> items= new ArrayList<>();

    RecyclerView recyclerView;
    MyAdapter adapter;

    Button btnLinear, btnGrid, btnAdd, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //대량의 데이터들 추가 - 실무에서는 서버나 DB 에서 읽어옴
        items.add(new Item("루피", "해적단 선장", R.drawable.crew_luffy,R.drawable.bg_one01));
        items.add(new Item("조로", "해적단 부선장", R.drawable.crew_zoro,R.drawable.bg_one02));
        items.add(new Item("나미", "해적단 항해사", R.drawable.crew_nami,R.drawable.bg_one03));
        items.add(new Item("상디", "해적단 요리사", R.drawable.crew_sanji,R.drawable.bg_one04));
        items.add(new Item("우솝", "해적단 저격수", R.drawable.crew_usopp,R.drawable.bg_one05));
        items.add(new Item("쵸파", "해적단 의사", R.drawable.crew_chopper,R.drawable.bg_one06));
        items.add(new Item("니코로빈", "해적단 고고학자", R.drawable.crew_nicorobin,R.drawable.bg_one07));


        recyclerView= findViewById(R.id.recycler);
        adapter= new MyAdapter(this,items);
        recyclerView.setAdapter(adapter);

        //선형배치(Linear) 버튼 클릭 시 
        btnLinear= findViewById(R.id.btn_linear);
        btnLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //리사이클러 뷰의 배치 관리자(LayoutManager)를 변경하기 + 새로 추가되는 아이템들을 역순으로(위로 쌓이듯이-피드, 타임라인) 보여줄 것인지 여부
                LinearLayoutManager layoutManager= new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(layoutManager);
            }
        });
        
        // 격자배치(Grid) 버튼 클릭 시
        btnGrid= findViewById(R.id.btn_grid);
        btnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GridLayoutManager gridLayoutManager= new GridLayoutManager(MainActivity.this,2);
                //GridLayoutManger의 두번째 파라미터 spanCount = 격자 갯수(2이면 한줄에 2개의 뷰), 세번째 파라미터 orientation = 뷰의 배치 순서(가로/세로우선)
                recyclerView.setLayoutManager(gridLayoutManager);
            }
        });

        //항목(하나의 뷰) 추가(Add) 버튼 클릭 시
        btnAdd= findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //데이터를 뷰(recyclerView)에 추가하는 것이 아니라, 대량 데이터 배열(items) 뒤에 데이터를 추가하는 것!!!!!!! 주의
                Item item= new Item("NEW","해적단 NEW",R.drawable.bg_one08,R.drawable.bg_one09);
                //items.add(item);
                
                //데이터 추가 등의 변경이 생겼을 경우, 어댑터에 변경사실을 공지(notify)해줘야 인식하여 화면이 갱신됨!
                //adapter.notifyDataSetChanged(); //모든 데이터 셋이 변경되었다는 의미. 모든 뷰를 처음부터 다시 만드는 작업 수행...비효율
                //adapter.notifyItemInserted(items.size()-1);
                //단, 실무적으로 봤을 때 새로운 게시글 추가 시 앞쪽에 붙는게 일반적 --> add 시 인덱스를 0번(시작점)으로 설정
                items.add(0,item);
                adapter.notifyItemInserted(0);  //포지션 맨 앞. 0번 자리
                
                //리사이클러 뷰의 스크롤의 위치를 첫번째 뷰 위치로 보내기
                recyclerView.scrollToPosition(0);       //.scrollTo(), .scrollBy() 는 활용이 어렵땅
            }
        });


        //항목(하나의 뷰) 제거(Delete) 버튼 클릭 시
        btnDelete= findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(0);
                adapter.notifyItemRemoved(0);   //.notifyItemChanged() : 안의 값이 바뀌었을 때 / .notifyItemMoved() : 아이템들의 위치가 바뀌었을 때
            }
        });

        //리사이클러 뷰의 아이템뷰를 클릭할 때 반응하는 리스너는 없음. 즉, 아이템뷰에 직접 클릭리스너를 설정 --> 아이템뷰를 만드는 코드 위치에서! (보통 VH 안의 Adapter)

    }
}