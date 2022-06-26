package com.grinleaf.ex084gsonlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.gson.Gson;
import com.grinleaf.ex084gsonlibrary.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn1.setOnClickListener(v->{
            //1. 기본 Json 파싱 클래스 이용
            String jsonStr= "{'name':'sam','age':20}";
            //json 객체 = {} 하나가 리사이클러뷰의 Item 하나라고 생각하면 됨! (값이 어떤 의미의 데이터인지 표현 가능)
            //{} 안의 문법 : {'식별자':'값', ...} 형태. 문자는 '' 안에, 숫자는 '' 없이 입력

            //2. name, age 를 멤버로 가진 Person 객체를 만들어 파싱한 값을 대입하기 : [ JSONObject 클래스 ]
            try {
                JSONObject jo= new JSONObject(jsonStr);   //파싱 한줄 컷~!
                String name= jo.getString("name");  //식별자 값으로 값 찾아오기
                int age= jo.getInt("age");

                //Person 객체를 만든 후 파싱한 값을 대입하기       --> 요 과정이 GSON 을 사용하면 사라짐!
                Person person= new Person(name, age);
                binding.tv.setText(person.name+", "+person.age);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        binding.btn2.setOnClickListener(v->{
            //3. GSON Library 를 이용하여 JSON 문자열을 곧바로 Person 객체로 분석(파싱)해줌  ==  JSON --> Person 객체
            String jsonStr= "{'name':'robin','age':25}";

            //GSON 으로 파싱하기
            Gson gson= new Gson();
            Person person= gson.fromJson(jsonStr,Person.class); //(json 형태로 된 String 값, 첫번째 값을 어떤 타입으로 만들 것인지(=해당 클래스의 정보를 값으로 줌))
            binding.tv.setText(person.name+"\n"+person.age);
        });

        binding.btn3.setOnClickListener(v->{
            //4. Person 객체 --> JSON 문자열로 변환하기
            Person person= new Person("hong",30);   //hong, 30 데이터를 --> {"age":30, "name":"hong"} 형식으로 자동으로 변환

            Gson gson= new Gson();
            String s= gson.toJson(person);
            binding.tv.setText(s);
        });
        
        binding.btn4.setOnClickListener(v->{
            //5. 네트워크에서 JSON 받아와서 파싱
            //네트워크 작업은 별도 스레드에서 할 것
            new Thread(){
                @Override
                public void run() {
                    String serverUrl= "http://grinleaf.dothome.co.kr/test.json";

                    try {
                        URL url= new URL(serverUrl);
                        InputStream is= url.openStream();
                        InputStreamReader isr= new InputStreamReader(is);
                        //여기서 BufferedReader 나 반복문으로 문서를 끝까지 읽어오는 코드가 필요했었음
//                        BufferedReader reader= new BufferedReader(isr);
//                        StringBuffer buffer= reader.readline();
//                        while(tr){ ... }

                        //Gson 이 그 작업을 대신 해줌! (Buffer 작업)
                        Gson gson= new Gson();
                        Person person= gson.fromJson(isr,Person.class);

                        runOnUiThread(()->{
                            binding.tv.setText(person.name+" : "+person.age);
                        });
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        });
        
        binding.btn5.setOnClickListener(v->{
            //6. jsonArray 를 Person[] 로 파싱
            String jsonStr= "[{'name':'sam','age':20},{'name':'robin','age':25}]";   //json 배열 객체 형태 : [] 안에 {}(=json 객체)이 여러 개 있음. --> [{},{},{}]

            Gson gson= new Gson();
            Person[] people= gson.fromJson(jsonStr,Person[].class);

            //ArrayList<> 로 쓸 땐 요렇게~
            ArrayList<Person> list= (ArrayList<Person>) Arrays.asList(people);

            StringBuffer buffer= new StringBuffer();
            for(Person p : people){
                buffer.append(p.name+":"+p.age+"\n");
            }
            binding.tv.setText(buffer.toString());
        });
    }
}