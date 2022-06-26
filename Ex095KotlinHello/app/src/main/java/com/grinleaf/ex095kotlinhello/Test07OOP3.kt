package com.grinleaf.ex095kotlinhello

fun main(){
    //안드로이드에서 가장 많이 사용되는 요소
    //1. 이너클래스
    //2. 인터페이스
    //3. 익명클래스
    //4. static 사용 불가 --> [ companion objcet ] : 동반 객체
    //5. 늦은 초기화 : late init, by lazy

    //1. 이너클래스 : java 에서는 원래 아우터 클래스에서만 사용가능한 클래스 --> 코틀린에서는 java 처럼 단순한 개념이 아닌, "inner" 클래스 키워드를 붙여야만 이너클래스가 됨
    //              아우터 클래스의 멤버를 내 멤버처럼 사용가능
    //AAA.BBB()   //inner 키워드 붙여야 error 가 남
    val obj= AAA()
    val obj2= obj.getBBBinstance()
    obj2.show()
    println()

    //2. 인터페이스 : 추상메소드가 보유한 클래스
    //val listener= ClickListener()   //인터페이스는 객체 바로 생성 불가 --> 인터페이스를 구현하는 클래스 객체를 생성해야함
    val listener= Test()
    listener.onClick()

    //3. 익명클래스 이용 : 2번과 동일하게 인터페이스의 추상메소드 사용을 위해 클래스 객체 생성 + 메소드 호출 하는 코드 축약 기법 (main() 밖에 굳이 클래스 설계하지 않고 바로 사용)
    val c= object:ClickListener{
        override fun onClick() {
            println("익명 클래스 click")
            println()
        }
    }
    c.onClick()

    //4. 동반객체 [ companion object ] (= static 키워드와 유사한 기능) : 객체 생성없이 바로 호출 가능한 멤버들
    //코틀린은 static 키워드가 없음!
    //println(Sample.a)   //error (클래스의 멤버를 사용하려면 반드시 클래스 객체를 만든 후 호출해야함 or 멤버에 static 키워드를 줘야하는데, 코틀린은 그게 읎다)
    println(Sample.title)

    //5. 늦은 초기화
    //1) lateinit : var 변수에 적용 (but, 기본타입에는 적용 불가)
    val h= Hello()
    h.onCreate()
    h.show()
    //2) by lazy  : val 변수에 적용 (기본타입에도 적용 가능)
}

//5-1. 늦은 초기화
class Hello{
    //var name:String?= null    //초기화를 나중에 하기 위해서 java 에서는 일단 null 을 넣어놓는 편 (코틀린에서 "?" 연산자를 일일이 붙여줘야함)
    lateinit var name:String    //초기화를 미룰 수 있음! MainActivity.java 에서 onCreate() 이전에 미리 TextView tv; 해놓을 때 사용
    
    //lateinit var age:Int      //error (기본 타입에는 사용 불가)
    //lateinit val kkk:String   //error (val 에도 사용 불가)
    // [ by lazy ] : {} 안에 실행문은 이 프로퍼티가 처음 사용될 때 실행됨 + 기본 타입에도 사용 가능
    val address:String by lazy{"aaa"}
    val age:Int by lazy{
        if(10>5) 20
        else 30
    }

    fun onCreate(){
        name= "sam"
    }

    fun show(){
        println("name : $name")
        println("address : $address")   //by lazy 키워드 적은 변수는 해당 변수가 최초로 실행될 때 {}를 실행함. println() 호출 시 실행
        println("age : $age")
    }
}

//4-1. 동반객체 (companion object)
class Sample{
    var a:Int= 10
    companion object{   //static 역할을 대신함. 이 영역 안에 들어있는 멤버들은 static 특성을 띔 (프로퍼티, 메소드 둘다 가능)
        var title:String= "Hello"
        fun show(){
            println("제목: $title")
        }

        //a= 100  //error (클래스의 프로퍼티는 객체를 생성해야만 사용 가능하므로, 클래스 객체 생성이 필요없는 companion object 와는 결이 맞지 않음)
        //코틀린에서는 어차피 완전체 객체지향이 아니기 때문에, main() 밖에 선언하여 전역변수,함수로 만들어쓰면 되니까... static 의 필요성이 별로 없음
    }
}

//2-1. 인터페이스
interface ClickListener{
    //추상 메소드
    fun onClick()
}

class Test:ClickListener{   //인터페이스 implements 할 때는 생성자 호출 필요 X "()" 생략
    override fun onClick() {    //->
        println("click")
        println()
    }

}


//1-1. 클래스 안에 클래스 생성
class AAA{
    var a:Int= 0

    fun show(){
        println("AAA 클래스의 show method...")
    }

    //이너클래스 객체를 만들어서 리턴해주는 메소드
    fun getBBBinstance():BBB{
        return BBB()
    }

    //inner class
    inner class BBB{
        fun show(){
            println("AAA 클래스의 프로퍼티 a : $a")   //기존 java 에서 아우터 클래스 멤버를 가져다 쓸 수 있는 특징이 코틀린에서는 인정되지 않음. "inner" 붙여야 error 사라짐

            //이너클래스에서 아우터의 메소드 호출해보기 ★★
            //show()            //요건 본인의 show()로 인식됨 (재귀호출)
            this@AAA.show()     //@label 사용!
        }
    }
}