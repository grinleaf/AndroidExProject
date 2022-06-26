package com.grinleaf.ex022actionviewactionmode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //1. ActionView Layout - OptionMenu Item 을 클릭했을 때 지정한 layout 모양으로 뷰가 펼쳐져서 보여짐
    //2. ActionMode - 제목줄(ActionBar)을 덮는 새로운 ActionBar 에 옵션메뉴를 구성하는 모드
    
    EditText actionViewEt;

    Button btn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn= findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ActionMode 시작하기 - 액션바 위치에 메뉴가 보여짐
                MainActivity.this.startActionMode(new ActionMode.Callback() {

                    //ActionMode 처음 시작할 때 메뉴들을 만들기 위해 한 번 호출되는 메소드
                    @Override
                    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                        getMenuInflater().inflate(R.menu.actionmode,menu);

                        actionMode.setTitle("action mode");
                        actionMode.setSubtitle("this is action mode");

                        return true;        //리턴값이 true 가 아니면 액션모드 발동하지 않는다! ★
                    }
                    //매번 액션모드가 열릴 때마다 발동하는 메소드
                    @Override
                    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                        return false;
                    }
                    
                    //액션모드에 만든 메뉴아이템들이 클릭되면 발생하는 메소드
                    @Override
                    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case R.id.menu_aa:
                                Toast.makeText(MainActivity.this, "share", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_bb:
                                Toast.makeText(MainActivity.this, "map", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_cc:
                                Toast.makeText(MainActivity.this, "dialer", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }

                    //액션모드가 종료될 때 발생하는 메소드
                    @Override
                    public void onDestroyActionMode(ActionMode actionMode) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option,menu);
        
        //ActionView 를 가지고 있는 MenuItem 객체 참조
        MenuItem item= menu.findItem(R.id.menu_action);

        //LinearLayout layout= (LinearLayout) item.getActionView();
        View view= item.getActionView();  //윗줄과 동일(형변환하기 싫으면~)
        actionViewEt= view.findViewById(R.id.actionview_et);

        //EditText 의 액션버튼(소프트키보드 우하단 색상버튼)을 클릭 했을 때 반응하는 리스너 적용
        actionViewEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                //두번째 파라미터 i : 소프트키보드에서 어떤 키를 눌렀는지 해당 식별자
                if(i == EditorInfo.IME_ACTION_SEARCH){  //IME : Input MEthod
                    String msg= actionViewEt.getText().toString();
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}