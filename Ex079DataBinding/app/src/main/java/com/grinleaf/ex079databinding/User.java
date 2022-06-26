package com.grinleaf.ex079databinding;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class User {

    //2-4. 일반 자료형은 데이터바인딩에 의해 값변경이 감시되지 않는다. (데이터 바인딩에 쓰이는 변수는 옵저빙(=관찰)이 가능한 특수한 변수여야함)
//    String name;
//    int age;
    public ObservableField<String> name= new ObservableField<>();    //기본형이 아닌 모든 자료형들은 Field<자료형> 클래스를 쓴당
    public ObservableInt age= new ObservableInt();

    //2-5. 생성자
    public User(String name, int age){
        this.name.set(name);    //기존 데이터를 옵저버블 변수에 대입
        this.age.set(age);
    }

    //2-12-c. 멤버변수 값을 변경하는 기능메소드 : activity_main.xml 문서의 onClick 속성에 의해 발동하는 특별한 콜백 메소드
    //규칙 : public .. 리턴값 void, 파라미터 View view
    public void changeData(View view){
        this.name.set("hong");
        this.age.set(30);
    }
}
