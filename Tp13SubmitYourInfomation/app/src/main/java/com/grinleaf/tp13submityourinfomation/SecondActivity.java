package com.grinleaf.tp13submityourinfomation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    EditText etName, etNick, etTitle, etText;
    TextView etCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        etName= findViewById(R.id.et_name);
        etNick= findViewById(R.id.et_nick);
        etTitle= findViewById(R.id.et_title);
        etText= findViewById(R.id.et_text);
        etCount= findViewById(R.id.char_count);

        //글자수 반영
        String countChar= etCount.getText().toString();;
        etCount.setText("글자수 : " + (countChar.length()) + "/500");
        etText.addTextChangedListener(new TextWatcher() {
           @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {   //파라미터 (입력된 문자열, start, count, after)
                if (charSequence.length() < 500) {                                              //count 값 대신 문자열.length() 써야 글자수 변화가 반영됨.
                    etCount.setText("글자수 : " + (countChar.length()) + "/500");
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {   //파라미터 (입력된 문자열, start, before, count)
                String count= charSequence.toString();
                if(count.length()<=500) {
                    etCount.setText("글자수 : " + (count.length()) + "/500");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        findViewById(R.id.btn_cancel).setOnClickListener(view -> {
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle("정말로 취소하시겠습니까?");
            builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            AlertDialog dialog= builder.create();
            dialog.show();
        });

        findViewById(R.id.btn_edit).setOnClickListener(view -> {
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle("작성을 완료하시겠습니까?");
            builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    String name= etName.getText().toString();
                    String nick= etNick.getText().toString();
                    String title= etTitle.getText().toString();
                    String text= etText.getText().toString();

                    Intent intent= getIntent();
                    intent.putExtra("name",name);
                    intent.putExtra("nick",nick);
                    intent.putExtra("title",title);
                    intent.putExtra("text",text);

                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
            AlertDialog dialog= builder.create();
            dialog.show();
        });

    }
}