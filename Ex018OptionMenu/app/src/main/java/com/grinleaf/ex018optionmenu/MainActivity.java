package com.grinleaf.ex018optionmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //액티비티는 제목줄(ActionBar)에 메뉴를 만들어내는 기능메소드가 이미 존재한다.
    //다만, 메뉴항목(MenuItem)이 없어 보이지 않는 것일 뿐, 메뉴항목을 만들어 Menu 영역에 추가해주는 코드를 작성하는 영역메소드가 있음
    //화면을 보여주는 기능을하는 onCreate 메소드 실행 이후 자동으로 실행(오버라이드 메소드)되는 onCreateOptionMenu 메소드!

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //자바코드로 메뉴항목(MenuItem) 추가하기
        // --> 각 메뉴항목들에 클릭 이벤트 발생 시, 상황별 기능 설정을 위해 식별(id부여)이 필요하고, 아이콘 등 설정이 매우 복잡해짐(선호하지 않음)
        menu.add("SEARCH");

        //실무에서는 메뉴를 별도의 xml 문서에 항목들을 따로 작성하여, menu 에 읽어와 추가하는 형태로 코딩하는 방법을 선호하는 편!!
        //xml 문서를 읽어와서 객체로 만들어주는 객체 : [ inflater ]
        // --> menu 폴더 안에 있는 xml 을 MenuItem 객체로 만들어주는 객체 : [ MenuInflater ]
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.option, menu);
        //운영체제(OS)에 접근을 대신해주는 Context 객체 - Context 를 상속하는 Activity - Activity 를 상속하는 MainActivity
        //MainActivity 는 Context 의 기능을 사용 가능!! ★★

        return super.onCreateOptionsMenu(menu);
    }

    //OptionMenu 의 메뉴항목(MenuItem)을 선택했을 때 자동으로 발동하는 콜백메소드
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //선택한 메뉴항목의 식별자 얻어오기
        int id= item.getItemId();

        switch(id){
            case R.id.menu_search:
                Toast.makeText(MainActivity.this,"Search",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_add:
                Toast.makeText(MainActivity.this,"Add",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_help:
                Toast.makeText(MainActivity.this,"help",Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}