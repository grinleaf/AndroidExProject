package com.grinleaf.ex095kotlinhello

//보조 생성자로 적용해보기 : 부모 클래스 옆에 () 를 쓰지 않음
class Professor:Person {

    var subject:String?= null   //자식 고유의 멤버변수. 보조 생성자의 파라미터로 사용하기 위한 선언 --> 코틀린에서는 null 값을 저장하는 타입을 따로 명시함 "?"

    constructor(name:String, age:Int, subject:String):super(name, age){
        //상속받은 부모 클래스의 생성자를 호출하지 않은 경우, 보조 생성자에서 super() (=부모클래스 생성자) 명시해줘야함
        this.subject= subject
        println("create Professor instance")
    }

    override fun show(){
        //super.show()
        println("name : $name   age : $age   subject : $subject")
    }
}