package com.grinleaf.ex024listview2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //대량의 데이터를 저장할 리스트 객체
    ArrayList<String> datas= new ArrayList<>();

    ArrayAdapter adapter;
    ListView listView;

    EditText et;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1. 대량의 데이터 추가 - 원래 실무에서는 직접 추가 대신, 서버나 DB 에서 데이터를 읽어오는 코드가 여기 자리하겠지용??
        datas.add(new String("aaa"));
        datas.add(new String("bbb"));
        datas.add(("ccc")); //자동 new String()

        //2. 각각의 item 들에 적용할 layout 형태 제작 (listview_item.xml 문서 만들기)
        //3. 대량의 데이터를 xml 레이아웃 모양의 뷰 객체들로 만들어주는 Adapter 객체 생성
            //adapter= ArrayAdapter.createFromResource()~ //이 방법은 xml 문서 안에 대량의 데이터가 존재할 때만 사용 가능
        adapter= new ArrayAdapter(this,R.layout.listview_item, datas);
        //4. activity_main.xml 에 ListView 추가 후 선언 및 설정
        listView= findViewById(R.id.listview);
        listView.setAdapter(adapter);

        //EditText 에 써있는 글씨를 얻어와 리스트뷰 안에 항목(item) 추가하여 보여주고자 함
        //5. activity_main.xml 에 EditView 와 Button 추가 후 선언 및 설정
        et= findViewById(R.id.et);
        btn= findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                String data= et.getText().toString();

                //5-1. 새로운데이터 추가
                //새로운 데이터 추가 시, 리스트뷰나 Adapter 객체에 추가하면 안 됨!
                //실제 대량의 데이터를 가지고 있는 ArrayList 객체나 String 배열 객체에게 새로운 데이터를 추가해줘야함!
                //Adapter 가 자동으로 추가된 뷰를 만들어 리스트뷰에 전달할 것!
                datas.add(data);

                //5-2. 화면 갱신 [ .notifyDataSetChanged() ] (Adapter 객체의 메소드)
                //새로운 데이터가 추가되어 ArrayList 나 String 배열 객체가 변경되었다는 사실을 Adapter 에게 알려야 화면이 갱신됨!
                adapter.notifyDataSetChanged();
                
                //5-3. 리스트뷰의 스크롤 위치 지정 가능 [ .setSelection(화면 커서를 두고자하는 배열 인덱스번호) ] (ListView 객체의 메소드)
                    //이 기능을 추가하지 않으면 추가 데이터의 양이 화면밖으로 넘어갔을 때, 화면 밖에서 데이터 추가됨(직접 스크롤해야 보임)
                listView.setSelection(datas.size()-1); //파라미터 : 배열의 크기-1 == 마지막 요소의 인덱스 번호 : 마지막 item 의 위치로 스크롤 이동

                et.setText("");
            }
        });

        //+6. 리스트뷰의 항목을 롱클릭하여 해당 항목삭제하는 기능
            // [ ArrayList 의 요소.remove(); ] -> [ Adapter 객체에게 변경사항 알리기(.notifyDataSetChanged();) ]
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                //클릭한 위치(position-세번째 파라미터 i 활용)의 데이터를 삭제
                datas.remove(i);
                adapter.notifyDataSetChanged();

                return true;
            }
        });

        //+7. 6번 기능으로 인해 리스트뷰의 항목이 모두 비워졌을 경우 보여지는 뷰 설정 : activity_main.xml 문서에 TextView 추가
        TextView tv= findViewById(R.id.tv);
        listView.setEmptyView(tv);
    }
}