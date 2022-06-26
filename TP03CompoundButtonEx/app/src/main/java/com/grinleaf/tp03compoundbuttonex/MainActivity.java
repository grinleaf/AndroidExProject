package com.grinleaf.tp03compoundbuttonex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //뷰 객체별 참조변수
    EditText name, phone1, phone2, phone3;
    RadioButton gender1, gender2, city1, city2, city3;
    CheckBox check1, check2, check3, check4;
    Button btn;
    TextView result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //객체 참조
        name= findViewById(R.id.name);
        phone1= findViewById(R.id.phone01);
        phone2= findViewById(R.id.phone02);
        phone3= findViewById(R.id.phone03);

        gender1= findViewById(R.id.gender01);
        gender2= findViewById(R.id.gender02);

        city1= findViewById(R.id.city01);
        city2= findViewById(R.id.city02);
        city3= findViewById(R.id.city03);

        check1= findViewById(R.id.check01);
        check2= findViewById(R.id.check02);
        check3= findViewById(R.id.check03);
        check4= findViewById(R.id.check04);

        btn= findViewById(R.id.btn);
        result= findViewById(R.id.result);

        //Radio, checkbox 리스너 객체 생성 및 설정
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s= "";
                s= name.getText().toString()+" ";

                if(gender1.isChecked()) s+= gender1.getText()+" ";
                if(gender2.isChecked()) s+= gender2.getText()+" ";
                if(city1.isChecked()) s+= city1.getText()+" ";
                if(city2.isChecked()) s+= city2.getText()+" ";
                if(city3.isChecked()) s+= city3.getText()+" ";

                s+= phone1.getText()+"-"+phone2.getText()+"-"+phone3.getText()+" ";

                if(check1.isChecked()) s+=check1.getText()+" ";
                if(check2.isChecked()) s+=check2.getText()+" ";
                if(check3.isChecked()) s+=check3.getText()+" ";
                if(check4.isChecked()) s+=check4.getText()+" ";

                result.setText(s);
            }
        });

        phone1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>=3){
                    phone2.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        phone2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>=4){
                    phone3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
