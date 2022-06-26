package com.grinleaf.ex089firebasefirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.grinleaf.ex089firebasefirestore.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Firebase Cloud Firestore (=Database) : No-SQL 방식 - RDBMS 처럼 테이블형식으로 저장되지않는 DBMS
    
    //Firebase Firestore 와 앱 연동 : 컬렉션 Collection (=테이블명) / Document(=각 row 당) / Field(=컬럼명)

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSave.setOnClickListener(v->clickSave());
        binding.btnLoad.setOnClickListener(v->clickLoad());
        binding.btnRealtimeLoad.setOnClickListener(v->clickRealtimeLoad());
        binding.btnSearch.setOnClickListener(v->clickSearch());
    }

    void clickSave(){
        //1. 저장할 데이터 받기
        String name= binding.etName.getText().toString();
        int age= Integer.parseInt(binding.etAge.getText().toString());
        String address= binding.etAddress.getText().toString();

        //2. Firebase DB 에 데이터 저장 (Map Collection 으로 저장)
        Map<String, Object> person= new HashMap<>();
        person.put("name",name);
        person.put("age",age);
        person.put("address",address);

        //3. Firebase Firestore 관리 객체 소환
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();

        //4. 기존 DB의 테이블명처럼 사용되는 것이 Collection --> people 컬렉션 만들기
        CollectionReference peopleRef= firebaseFirestore.collection("people");
        //5. 성공했는지 확인하기위해서 리턴받기. 확인 안할거면 필요없당
        //Task task= peopleRef.document().set(person);   //자동으로 랜덤한 이름의 Document 생성. 그 안에 person 데이터 저장
//        Task task= peopleRef.add(person);   //위의 코드 줄여쓰기
//        task.addOnSuccessListener(new OnSuccessListener() {
//            @Override
//            public void onSuccess(Object o) {
//                Toast.makeText(MainActivity.this, "save Success", Toast.LENGTH_SHORT).show();
//            }
//        });

        //add() 사용 시 Document 명이 랜덤하게 만들어지고, DB 에는 이름정렬순이므로 저장된 데이터를 읽어올 때 순서가 뒤집어질 수 있음
        //5-1. 직접 Document 명을 명시하여 순서를 지정할 수 있음
//        firebaseFirestore.collection("member").document("1").set(person); //새로운 테이블 생성 + document 명 지정
//        firebaseFirestore.collection("member").document("2").set(person);
//        firebaseFirestore.collection("member").document("3").set(person);

        //5-2. 날짜와 시간을 이용하여 순차적으로 저장되도록 할 수 있음
        //System.currentTimeMillis() : 1970년 01월 01일 0시 0분 0초부터 1ms 마다 카운트
        firebaseFirestore.collection("member").document(System.currentTimeMillis()+"").set(person);
        
        //5-3. 날짜와 시간을 SimpleDateFormat() 객체 활용하여 지정할 수도 있음!
        
        //6. 저장할 데이터를 굳이 HashMap 으로 만들지 않고, 값들을 멤버로 가진 데이터 객체를 한번에 set 할 수 있음 --> error
//        PersonVO p= new PersonVO(name, age, address);
//        firebaseFirestore.collection("user").add(p);
    }

    void clickLoad(){
        binding.tv.setText("");

        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        CollectionReference memberRef= firebaseFirestore.collection("member");
        Task<QuerySnapshot> task= memberRef.get();  //Task<QuerySnapshot> : 요청한 순간의 스냅샷(사진)에서 정보를 얻어온다는 의미.
        task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot snapshots= task.getResult();
                    //task.getResult()가 QuerySnapshot 을 반환 --> 결과 데이터가 여러 개인 경우가 많음! (즉, document 가 여러 개)
                    for(DocumentSnapshot snapshot:snapshots){
                        //Document 별로 그 순간의 데이터를 취득한 DocumentSnapshot 에서 필드값들 얻어오기
                        Map<String, Object> person= snapshot.getData();
                        String name= person.get("name").toString();
                        int age= Integer.parseInt(person.get("age").toString());
                        String address= person.get("address").toString();

                        binding.tv.append(name+", "+age+", "+address+"\n");
                    }
                }
            }
        });
    }

    void clickRealtimeLoad(){
        //"member" 컬렉션의 데이터 변화를 실시간 감지하기 --> 채팅 시스템에 사용 가능
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        CollectionReference memberRef= firebaseFirestore.collection("member");
        memberRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                //파라미터 ( 요청한 순간의 스냅샷 값, 예외 )
                StringBuffer buffer= new StringBuffer();
                for(DocumentSnapshot snapshot:value){   //clickLoad()의 for 문과 형태 동일
                    Map<String, Object> person= snapshot.getData();
                    String name= person.get("name").toString();
                    int age= Integer.parseInt(person.get("age").toString());
                    String address= person.get("address").toString();

                    buffer.append(name+", "+age+", "+address+"\n");
                }
                binding.tv.setText(buffer.toString());
            }
        });
    }

    void clickSearch(){
        //"member"컬렉션에서 특정 필드 값에 해당하는 데이터들만 가져오기 --> 검색 기능에 사용!
        String name= binding.etName.getText().toString();
        
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        CollectionReference memberRef= firebaseFirestore.collection("member");
        memberRef.whereEqualTo("name",name).addSnapshotListener(new EventListener<QuerySnapshot>() {    //원하는 값만 찾아오기
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                //같은 이름이 여러 개일 수 있으므로 for 문 돌려서 가져오기
                StringBuffer buffer= new StringBuffer();
                for(DocumentSnapshot snapshot: value){
                    Map<String, Object> person= snapshot.getData();
                    String name= person.get("name").toString();
                    int age= Integer.parseInt(person.get("age").toString());
                    String address= person.get("address").toString();

                    buffer.append(name+", "+age+", "+address+"\n");
                }
                binding.tv.setText(buffer.toString());
            }
        });
    }
}