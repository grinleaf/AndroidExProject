package com.grinleaf.ex095kotlinhello

open class Student constructor(name:String, age:Int, var major:String):Person(name, age) {
    //상속받은 Person 에 넘겨줄 Person 멤버변수(name:String, age:Int)들을 매개변수로 생성자에 두기 --> 매개변수를 바로 상속받은 클래스 파라미터(name, age)로 넘겨주기
    init {
        println("create Student instance")
    }

    //override : 자동으로 open 으로 적용됨
    override fun show(){
        //super.show()
        println("name : $name  age : $age  major : $major")
    }
}