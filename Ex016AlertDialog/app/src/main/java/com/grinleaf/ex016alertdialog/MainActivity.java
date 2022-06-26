package com.grinleaf.ex016alertdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button btn, btn02, btn03, btn04, btn05;

    //목록(리스트) 다이얼로그에서 보여줄 항목들(Items) 배열객체
    String[] items= new String[]{"Java", "XML", "Kotlin", "C++"};
    //다중 선택 다이얼로그에서 쓰일 boolean 배열객체
    boolean[] checked= new boolean[]{true,true,false,false};

    int whitch= 2; //선택 항목 index

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn= findViewById(R.id.btn);
        btn02= findViewById(R.id.btn02);
        btn03= findViewById(R.id.btn03);
        btn04= findViewById(R.id.btn04);
        btn05= findViewById(R.id.btn05);

        btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);

                //1. 자바문서 안에서 자바언어로 "커스텀 뷰" 만들기
//              ImageView iv= new ImageView(MainActivity.this);
//              iv.setImageResource(R.drawable.ms16);
//
//              TextView tv= new TextView(MainActivity.this);
//              tv.setText("메이플 스토리");
//              tv.setTextSize(24);       //자바 문서에서의 사이즈 단위는 무조건 px..
//
//              //다이얼로그도 한 개의 뷰만 설정(set)이 가능! 뷰 그룹으로 여러 개 보여주는 기법 써야함
//              LinearLayout layout= new LinearLayout(MainActivity.this);
//
//              layout.setOrientation(LinearLayout.VERTICAL);
//              layout.addView(iv);
//              layout.addView(tv);
//
//              builder.setView(layout);
                
                //2. xml 문서에서 "커스텀 뷰" 만들어 가져오기
                builder.setView(R.layout.dialog);

                AlertDialog dialog= builder.create();
                dialog.show();
            }
        });

        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("사용 가능한 언어 모두 선택");
                builder.setMultiChoiceItems(items, checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        //두번째 파라미터 i : 체크를 변경한 항목의 index 번호
                        //세번째 파라미터 b : 바뀐 결과 true/false
                        checked[i]= b;
                    }
                });
                builder.setPositiveButton("완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String s="";

                        for(int k=0;k<checked.length;k++){
                            if(checked[k]) s+= items[k];
                        }

                    Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog= builder.create();
                dialog.show();
            }
        });

        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("싱글 선택 다이얼로그");
                builder.setSingleChoiceItems(items, 2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //두번째 파라미터 i : 선택 항목의 index 번호
                        whitch= i;
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, items[whitch], Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog= builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("개발언어 선택");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //두번째 파라미터 i : 선택 항목의 위치 index 를 의미함. 0번부터 시작!
//                      Toast.makeText(MainActivity.this, i+"", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, items[i], Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog= builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //AlertDialog 를 요청받아 대신 만들어주는 Builder 객체 생성 및 설정
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);

                //Builder 에게 원하는 다이얼로그 모양 의뢰(설정)
                builder.setTitle("다이얼로그");
                builder.setIcon(R.mipmap.ic_launcher_round);
                builder.setMessage("정말로 종료하시겠습니까~");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "OK",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"CANCEL",Toast.LENGTH_SHORT).show();
                    }
                });

                //Builder 에게 의뢰한 대로 AlertDialog 객체를 만들어 달라고 요청
                AlertDialog dialog= builder.create();

                //다이얼로그 창이 아닌 바깥 쪽을 터치했을 때(예외상황) 다이얼로그가 없어지지 않도록 제한이 필요함!
                dialog.setCanceledOnTouchOutside(false);    //default 값이 true!
                dialog.setCancelable(false);                //바깥 쪽 터치 중 뒤로가기버튼 눌렀을 경우 꺼지는 상황도 false 로 제한

                //다이얼로그 보여주기
                dialog.show();

            }
        });

    }
}