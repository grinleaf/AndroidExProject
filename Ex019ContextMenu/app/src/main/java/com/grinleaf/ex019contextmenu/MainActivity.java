package com.grinleaf.ex019contextmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn= findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Clicked",Toast.LENGTH_SHORT).show();
            }
        });

        //버튼을 길게 클릭(Long Click) 했을 때 메뉴가 자동으로 보여지도록 Context Menu 로 등록
        this.registerForContextMenu(btn);
    }
    
    //Context Menu 로 등록된 버튼이 Long Click 되면, 메뉴를 보여주기 위해 메뉴항목을 만들어내는 기능 메소드가 자동으로 발동됨

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        //res/menu 폴더 안에 별도의 context.xml 문서를 만들어 메뉴항목 설정
        //context.xml 파일을 메뉴객체로 만들어주는 inflater 객체를 이용하여 제작
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.context,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    //Context 메뉴항목을 선택했을 때 자동으로 발동하는 콜백 메소드

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_save:
                Toast.makeText(MainActivity.this,"SAVE",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_delete:
                Toast.makeText(MainActivity.this,"DELETE",Toast.LENGTH_SHORT).show();
                break;
        };
        return super.onContextItemSelected(item);
    }
}