package com.grinleaf.ex095kotlinhello

import android.app.AlertDialog

fun main(){
    //NPE [ Null Pointer Exception ] 에 대한 앱의 버그를 문법적으로 막아주기 위한 Null Safety(널 안정성) 관련 문법

    //코틀린은 null 값을 저장할 수 있는 타입을 명시적으로 구분하도록 함
    //var s1:String= null   //non nullable variable     //error (null을 넣기 위한 타입을 명시해야함)
    var s2:String?= null    //nullable variable
    println(s2)

    //nullable 변수를 사용할 때 특이점
    var str1:String= "Hello"
    var str2:String?= "Nice"
    println("글자수 : "+str1.length)   //OK
    //println("글자수 : "+str2.length)   //error (null 값이 나오면 error 가 발생하므로, error 일수도 있다는 의미의 문법적 오류로 빌드를 막음)

    //1)-0. 해결책 1 : 기존 방식
    if(str2!=null) println("글자수 : "+str2.length)    //OK

    //1) 해결책 2 : 해결책1의 번거로운 if 문 제거하기 위한 null safety 연산자 사용 : [ ?. ]
    println("글자수 : "+ str2?.length)
    //이 때, 값이 정말 null 이라면 "null" 을 찍어내고, 아니면 .length 를 나타낸다
    str2= null
    println("글자수 : "+str2?.length)
    //--> null 일 때 null 값이 아닌 특정 문자열 등 다른 결과값이 나오도록 하기
    //ex) null 이면 -1 출력, 아니면 글자수 출력

    //2)-0. 기존 방식
    var len= if(str2==null) -1 else str2.length
    println("글자수 : $len")

    //2) if() else()이 번거롭다면 앨비스 연산자 사용 : [ ?: ]
    val len2= str2?.length?:-1

    //3) NPE 에러를 그냥 발생시키면서 Java 처럼 코딩하고 싶을 때 non-null asserted call 연산자 사용 : [ !! ]
    var ss:String?= "Hello"
    //println(ss.length)  //error
    println(ss!!.length)

    //3)-1. 실제로 null 값이 있을 때
    var ss2:String?= null
    //println(ss2!!.length)    //NullPointerException 메시지 출력됨

    //4) 안전한 형변환 : [ as? ]
    val mmm:MMM= MMM()

    //val zzz:ZZZ= mmm as ZZZ
    //println(zzz.a)      //ClassCastException 메시지 출력
    
    val zzz:ZZZ?= mmm as? ZZZ   //안전한 cast as? 를 사용했다면, 결과가 null 일 수도 있다는 의미 --> 변수에도 ? 연산자를 줘야함
    println(zzz)

    //5) scope function : apply / let / run / also

    //5)-1. apply : 특정 객체의 멤버를 여러 개 사용할 때
    val crew= Crew()
    crew.name= "sam"
    crew.age= 20
    crew.address= "seoul"
    crew.show()         //클래스의 멤버 호출 시 "객체명.멤버" 형식으로 호출하다보면 오타 등 오류의 여지가 있음 --> apply 사용

    val crew2= Crew()
    crew2.apply {
        //this.name= "robin"        //.apply 로 영역 안에서 멤버를 호출 (오류 방지)
        //this.age= 25
        //this.address= "busan"     //본인 영역 안에서의 this. 은 생략 가능!
        name= "robin"
        age= 30
        address= "busan"
        show()
    }
    // --> apply EX) AlertDialog 예시
//    val builder:AlertDialog.Builder= AlertDialog.Builder(this)
//    builder.apply {
//        setTitle()
//        setMessage()
//        setPositiveButton() ...
//    }

    //5)-2. let : apply 와 사용법 동일 (this 대신 it 을 사용함 = 람다식 용법)
    val crew3= Crew()
    crew3.let {
        it.name="hong"
        it.age= 30
        it.address= "incheon"
        it.show()
        //마지막 부분만 다르.......다는데 못봤다 ㅇㅅㅇ......나중에 제대로 다시 해주신댕
    }
}

//5)-1. scope function - apply
class Crew(){
    var name:String?= null
    var age:Int?= null
    var address:String?= null

    fun show(){
        println("$name   $age   $address")
    }
}

class MMM{
    var a=10
}

class ZZZ{
    var a=20
}