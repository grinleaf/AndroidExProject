package com.grinleaf.ex095kotlinhello

import android.widget.TextView

//kotlin 문서 만들 때 class 와 file 로 나뉘지만, 실제로는 둘 다 .kt 문서임. 편집기에서 둘을 구분해서 보여주기 위해 만든 방식
//코틀린 언어 기초 문법 [ 코틀린 연습 사이트 : https://try.kotlinlang.org ]

// *** 문법적 주요 특징 ***
// A. 문장의 끝을 나타내는 ";" 을 사용하지 않음 - 무시됨
// B. 변수 선언 시 자료형을 먼저 명시하지 않음 - var, val 키워드 사용. 단, 자료형은 분명히 존재함!
// C. new 키워드 없이 객체를 생성함 - new String() --> String()
// D. 안전하게 null 을 다룰 수 있는 문법 제공 - "?" = nullable
// E. 함수형 언어(요즘 패러다임) - 함수를 객체처럼 변수에 저장가능/파라미터로 함수 넘겨주기 가능.
                            //객체 사용할 때마다 객체를 생성(+콜백메소드)(-->new 객체(new onXXX()))해야하는 번거로움 보완 (대표주자 : 람다식 표기법)

// #. 프로그램의 시작 함수인 main 함수가 반드시 있어야함.
// #. 함수 정의 시 리턴타입 위치에 'fun' 키워드(function 의 약자) 사용
fun main(){
    /* .kt 코틀린 파일만 별도로 실행하기 : 우클릭 - 해당파일 Run 하면, 하단 Run 탭에 실행 결과 표시됨 */

    //1. 화면 출력 : print(), println() 함수
    print(10)
    print(3.14)
    print("Nice")
    
    //print()는 자동 줄바꿈 x --> println() 쓰기
    println()
    println("Hello Kotlin")
    println(10)
    println(10.5)
    println('A')
    println(true)
    println(3+5)

    //변수명 전달 시 변수 안에 있는 값 출력
    var a:Int= 10
    println(a)
    var b:String= "Hello"
    println(b)
    println()
    ///////////////////////////////////////////////
    //2. 자료형과 변수

    // ** 코틀린 자료형의 종류 **
    // 1) 기초 타입 = 기본형 자료형 --> 단, 자바에서는 모든 자료형이 객체임! 주의할 것 (이 때문에 Int, Double 요런식으로 자료형 첫 글자가 대문자임)
    // : Boolean, Byte, Char, Short, Int, Long, Float, Double (총 8개) --> 다 객체의 참조변수임! . 찍으면 관련 함수 리스트업됨
    // 2) 참조 타입 = 참조형 자료형
    // : String(Kotlin/Java 다름-import 시 주의), Any(=Object 역할), Unit(=void) ... 그 외 Kotlin APIs(=기본 제공 클래스들)
    //   + Java APIs(코틀린 클래스보다 메소드가 더 많은 편)

    // ** 변수를 만드는 2 가지 키워드 : var, val **
    //2-1. var (=variable) : 값 변경이 가능한 변수 [ var 변수명: 자료형 ]
    var num:Int= 10
    println(num)
    var num2:Double= 3.14
    println(num2)
    //권장하지는 않으나, 변수만 먼저 만들고 나중에 값 대입해도 됨 (지역 변수만 가능-멤버변수는 값 대입 없으면 오류)
    var num3:Float
    num3= 5.23F     //5.23(기본 자료형 Double)
    println(num3)

    //변수이므로 변수가 가지고 있는 값 변경 가능
    num= 20
    num2= 50.5
    num3= 10.8F
    println(num)
    println(num2)
    println(num3)

    //자료형이 있는 변수이므로, 다른 자료형 대입 시 error
    //num= 3.14       //error (Int 변수에 Double 값 대입)
    //num2= 50        //error (Double 변수에 Int 값 대입) --> Java 에서는 허용되던 것 (자동 형변환 x)

    //명시적 형변환 : [ .toXXX()로 변환 가능 ] --> 기초타입만 사용 가능
    //num= (int)3.14    //코틀린에는 이런 문법 없음
    num= 3.14.toInt()
    num2= 50.toDouble()
    println(num)
    println(num2)

    //문자열 String 객체
    var s:String= "Hello"
    println(s)
    //var s2:String= String("Nice")   //error (코틀린의 String 클래스는 생성자에 문자열 데이터 받을 수 없음!)
    //buffer 나 byte 배열을 String 으로 만들 때만 String() 사용
    println()


    //2-2. val (=value) : 값 변경이 불가능한 변수 [ val 변수명: 자료형 ]    (상수 개념과 비슷하나, 엄밀히는 다른 개념)
    val n1:Int= 100
    //n1= 200     //error
    println(n1)

    val n2:Boolean= true
    //n2=false    //error
    println(n2)

    //권장하지는 않으나, 변수를 만들 때 값을 지정하지 않으면, 최초 한번은 값을 대입할 수 있음
    val n3:String
    n3= "Nice"
    println(n3)
    //n3= "Good"    //error
    println()

    //## var, val 사용 팁 ##
    //- var : 통상적으로 데이터를 가지는 변수 (ex- name, age, title 등 파싱값)
    //- val : 객체 참조에 사용하는 변수 (ex- TextView, ImageView NotificationManager 등)


    //2-3. 자료형을 생략하며 변수 선언이 가능함! (자료형 자동 추론)
    var aa= 10      //컴파일러가 자동으로 Int 로 추론
    println(aa)
    var bb= 3.14    //Double 추론
    println(bb)
    var cc= 3.14f   //Float 추론
    println(cc)
    var dd= true    //Boolean 추론
    println(dd)
    var ee= 'A'     //Char 추론
    println(ee)
    var ff= "Hello" //참조형 자료형도 추론됨
    println(ff)
    //자동 추론되어도 정적타입 언어 (정해진 자료형 외의 자료형 대입 불가)
    // --> JavaScript 가 동적 타입 언어 (변수에 여러가지 자료형을 대입 가능. 타입 개념 모호)

    //var gg  //error (자료형을 명시하지 않으면 타입 추론 x. 반드시 값 대입이 되어있어야 추론 가능)
    //gg= 10
    //var hh
    //hh= "aaa" //참조형도 마찬가지
    println()

    //2-4. 코틀린만의 자료형
    //- Any 타입 : 어떤 타입이든 참조 가능한 자료형 (=Java 의 Object 클래스. 즉, 최상위 클래스)
    var v:Any
    v= 10
    println(v)
    v= 3.14
    println(v)
    v= 3.14f
    println(v)
    v= 'A'
    println(v)
    v= "Hello"
    println(v)
    println()


    // ** 변수에 값을 대입할 때 특이점 : [ null 안전성 문법(null safety) - null 값을 저장할 수 있는 타입이 별도로 존재 ] **
    // if(aaa==null) return;    //java 에서 빈번하게 쓰던 이 null 안전성 구문을 스킵가능하게 해줌!
    
    //코틀린에서 자료형을 명시한 변수는 기본적으로 null 을 저장할 수 없음. null 값을 가질 수 있는 타입의 변수라는 것을 표시해줘야함 : [ nullable 변수 ]
    //var nn:Int= null    //error
    //var s2:String= null  //error
    var nn:Int?= null   //"?" : null 을 가질? 수도? 있음?
    var s2:String?= null
    println(nn)
    println(s2)
    println()
    
    //nullable 변수의 멤버 사용하기 : [ ?. ]
    var bbb:String?= "Hello"
    //print(bbb.length)   //error ( null 일수도 있기 때문에 = null 이면 문자열 길이잴 수 x)
    //if(bbb!=null) print(bbb.length)     //요렇게 써줘야함,,
    println(bbb?.length)    // "?." : null safety 연산자
    bbb= null
    println(bbb?.length)    //Null Point Error(NPE) 에러가 발생하지 않음!
    
    var t= null     //자동 추론 시 Any? 자료형이 됨
    println(t)
    println()

    // ** 화면 출력 format 만들기 **
    println("Hello"+" Kotlin")  //"+" 결합연산자 : String 클래스 concat() 발동

    //but, Number 타입에서 String 타입으로 자동 변환은 불가
    //println(10+"")  //error (자동형변환 x)
    println("Hello"+10)  //거꾸로 쓰면 됨;
    //숫자 먼저 쓰고 싶으면...
    println(10.toString()+"Hello")
    println(""+10+"Hello")
    println()

    //2 개의 정수형 변수 값을 덧셈하여 출력하는 프로그램 만들기
    var nnn1= 50
    var nnn2= 30
    //println(nnn1+"+"+nnn2+"="+(nnn1+nnn2))  //error
    println(""+nnn1+"+"+nnn2+"="+(nnn1+nnn2))   //맞는 방법이지만, 협업자의 이해를 돕기 위해서 아래 형식을 선호하는 편
    println(nnn1.toString()+"+"+nnn2.toString()+"="+(nnn1+nnn2))

    //위 2 가지 방법 모두 결합연산자 사용에는 코드 가독성이 떨어지고, 작성도 번거로운 편
    //--> php 형식을 가져옴 : [ 문자열 탬플릿 ] 문법
    println("$nnn1 + $nnn2 = ${nnn1+nnn2}") //"" 내에서 변수 여러 개의 연산을 쓸 때, ${}으로 묶을 것!

    var name= "sam"
    var age= 20
    println("이름 : $name\n나이 : $age")
    
}