package com.grinleaf.ex095kotlinhello

fun main(){
    println("Kotlin OOP")

    //1. 클래스 선언 및 생성 : new 키워드가 없음
    var obj= MyClass()
    obj.show()

    //2. 별도의 파일로 클래스를 만들 수도 있음 --> Kotlin 클래스로 파일 만들기
    var obj2= MyKotlinClass()
    obj2.show()
    
    //3. 생성자 : 많이...다릅니다...★★
    //코틀린의 생성자 : 주 생성자(Primary Constructor) / 보조 생성자(Secondary Constructor)
    //3-1. 주 생성자(Primary Constructor)
    var s= Simple()
    var s2= Simple2(10,200)
    var h= Human("sam",20,"seoul").show()

    //주 생성자는 오버로딩 X ★★ --> 대신 보조 생성자에서 처리

    //3-2. 보조 생성자(Secondary Constructor)
    val s3= Simple3()
    val ss3= Simple3(100)
    //3-3. 주 + 보조
    val s4= Simple4()
    val ss4= Simple4(100)
    //3-4. 주 생성자 constructor 키워드 생략
    val s5= Simple5()
    val s6= Simple6("robin",25)
}

//3-4. 주 생성자의 constructor 키워드 생략
class Simple6(var name:String, var age:Int){    //파라미터가 있을 경우에 constructor 키워드 생략이 용이
    init {
        println("Simple6 constructor")
    }
}

class Simple5(){    //원래 요거 : class Simple5 constructor()
    init {
        println("Simple5 constructor")
    }
}


//3-3. 주 생성자 + 보조 생성자
class Simple4 constructor(){
    init {
        println("Simple4 주 생성자")
    }
    //주 생성자가 있을 때, 보조 생성자는 반드시 명시적으로 주 생성자를 호출하는 코드를 써야만 오류가 나지 않음!
    constructor(num:Int):this(){    //this() 는 주 생성자를 호출하는 것. 만약 주 생성자에 파라미터 있었으면, this(여기도) 파라미터 기입해줘야함
                                    // --> 다른 생성자를 호출하는 키워드 : this() 나의 주 생성자 호출 / super() 부모의 주 생성자 호출
                                    //만약 파라미터 2개를 가진 보조 생성자가 있을 때 :this(a,b) 호출했으면, 해당 보조 생성자를 호출한 것임
        println("Simple4 보조 생성자 오버로딩 : $num")
    }
}

//3-2. 보조 생성자 연습 클래스 : 클래스 {} 안에 constructor 키워드 사용
class Simple3{
    constructor(){
        println("Simple3 보조 생성자")
        println()
    }
    constructor(num:Int){
        println("Simple3 보조 생성자 : $num")
        println()
    }
    //보조 생성자는 오버로딩 O ★★
    //보조 생성자의 파라미터에는 var, val 키워드 사용 불가 --> 멤버변수를 보조 생성자에서 사용할 때는 기존 방식대로 사용
    var name:String=""
    var age:Int=0
    constructor(name:String, age:Int){
        this.name= name
        this.age= age
    }
}

//3-1. 주 생성자 연습 클래스 : 클래스명 옆 constructor 키워드 사용
class Human constructor(var name:String, var age:Int, var address:String){
    fun show(){
        println("$name : $age : $address")
    }
}

class Simple2 constructor(num:Int, var num3:Int){ //주 생성자의 파라미터에 var, val 사용 시, 해당 변수가 멤버변수가 됨 (this.num3=num3 이과정을 축약한 것!)
    var num2:Int= num   //주 생성자의 매개변수를 멤버변수로 대입
    init {
        println("Simple2 primary constructor : $num")
        println("Simple2 primary constructor : $num2")
        println("Simple2 primary constructor : $num3")
    }
    fun show(){
        //println("num : $num")   //주 생성자의 매개변수(파라미터)는 init 영역 밖에서는 인식 불가능
        println("num2: $num2")    //멤버변수는 class 안에 어디서든 사용가능
        println("num3: $num3")
    }
}

class Simple constructor(){
    //주 생성자는 실행영역 {}이 없음! --> 초기화 영역 init() 존재
    init{
        //객체가 실행될 때 무조건 실행되는 영역
        println("Simple primary constructor")
        println()
    }
}

class MyClass{
    //멤버변수(= 자바에선 필드) [ Property : 프로퍼티 ] --> 자바와는 다르게 반드시 초기화되어야함!
    var a:Int= 10

    //멤버함수 [ Method : 메소드 ]
    fun show(){
        println("show : $a")
        println()
    }
}