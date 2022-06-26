package com.grinleaf.ex095kotlinhello

fun main(){
    //상속
    var obj= Second()   //Second s= new Second()
    obj.a= 30
    obj.b= 40
    obj.show()
    
    //업캐스팅 , 다운캐스팅
    var f:First= Second()   //부모가 자식 객체 참조 : 업캐스팅
    f.show()

    //f.aaa()                   //자식의 고유멤버변수, 함수는 인식 불가 --> 사용하고자 할 때 : 다운캐스팅
    //val s: Second= f          //error (자식이 부모 객체 참조하는 것으로 인식함)
    //val s: Second= (Second)f  //error (요것도 java 식 형변환)
    val s:Second= f as Second   // 다운캐스팅 형변환 키워드 [ as ]
    s.show()
    s.aaa()

    //상속 마무리 연습 - 일반회원 / 학생회원 / 교수회원 / 근로학생회원 --> 별도 클래스로 만듦!
    var p= Person("sam", 20)
    p.show()
    println()

    val stu= Student("robin",22,"kotlin android")
    stu.show()
    println()

    val alba= AlbaStudent("hong", 25, "java android", "PC Manager")
    alba.show()
    println()

    val pro= Professor("tom", 45, "mobile optimization")
    pro.show()
    println()
}

//상속해줄 클래스
open class First{
    //프로퍼티 = 멤버변수
    var a:Int= 10

    //메소드 = 멤버함수
    open fun show(){
        println("a: $a")
    }
}

//First 를 상속하는 클래스 : 코틀린의 상속은 ":" --> 반드시 부모클래스의 생성자()를 명시적으로 호출해야함 (주 생성자)
//                        코틀린은 기본적으로(default) 상속이 안되게 final 로 되어있음 --> 상속할 클래스에 "open" 키워드를 표기해야만 상속이 가능함
class Second : First(){
    var b:Int= 20

    //상속받은 show() 메소드의 기능 개선 - [ override ] --> 코틀린은 override 키워드를 반드시 명시해야만 한다!
    //코틀린은 기본적으로(default) 오버라이드 또한 final 로 막혀있으므로 "open" 키워드를 통해 허용해줘야 함!
    override fun show(){
        super.show()    //상속받은 부모클래스의 메소드 호출하는 키워드 super
        println("b: $b")
    }

    //자식 클래스가 가진 고유한 메소드
    fun aaa(){
        println("Second 클래스의 고유 메소드")
        println()
    }
}