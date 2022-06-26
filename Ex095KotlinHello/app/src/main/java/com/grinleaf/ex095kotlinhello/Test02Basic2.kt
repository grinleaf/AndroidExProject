package com.grinleaf.ex095kotlinhello

//무조건 main() 함수가 프로그램 시작점
fun main(){
    //3. 연산자 특이점 ///////////////////////////////////////////////////////////////////////////////
    //숫자타입 간의 연산은 자동형변환
    println(10+3.14)    //작은 자료형을 큰 자료형으로 자동형변환 (Double)

    //숫자타입이 아닌 자료형과는 자동형변환 X
    //println(10+true)    //error
    //println(10+'A')     //error (Char 자료형은 숫자타입이 아니므로 자동형변환 X)

    //** 자료형을 체크해주는 연산자 is (= Java 의 instanceof 와 비슷한 기능) **
    var n= 10
    if(n is Int){
        println("n는 Int타입 입니다.")
    }

    var s:String= "Hello"
    if(s is String) println("s 변수는 String 입니다.")
    if(s is String?) println("s 변수는 String 입니다.")
    //is 연산자는 nullable 을 다른 자료형으로 구분하지 않으므로 위아래 동일한 결과값이 나옴

    if(s !is String) print("s 는 String 이 아닙니다.")

    //is 는 해당 변수의 자료형 외에 다른 자료형을 문법적으로 검사해주지 않음
    //if(n is String){}   //error
    //**is 연산자는 사실상 Any 타입을 검사할 때 이용함!
    var obj:Any

    obj= 10
    if(obj is Int) println("${obj}는 Int 입니다.")
    if(obj is Double) println("${obj}는 Double 입니다.")

    class Person{
        //코틀린에서 멤버변수는 무조건 초기화되어있어야함
        var name:String= "sam"
        var age= 20
    }
    
    var p= Person()
    if(p is Person) println(p.name + ", "+p.age)
    
    var obj2:Any
    obj2= Person()  //업캐스팅
    if(obj2 is Person){     //Java 에서는 Person p= obj2; 문장을 통해 다운캐스팅 과정이 필요하나, is 연산자를 사용하면 스킵 가능
        //** is 조건이 참인 영역 안에서는 obj 참조변수가 자동 다운캐스팅됨 **
        println(obj2.name+" : "+obj2.age)
    }

    //코틀린은 비트 연산자가 없음! & | ^ ~ --> 대신, 비트 연산자에 대응하는 메소드들이 존재함 (표기법 가독성 UP)

    //* "|" 연산자 --> .or()
    //println(7 | 3)  //error (비트연산자 X)
    println(7.or(3))
    println(7 or 3) //요렇게 표기도 가능

    //* "&" 연산자 --> .and()
    println(true and false)
    //println(3.14 and 5.55)  //error (비트연산은 상수형(정수, 불린)만 가능. Double 형 불가)
    //println("aaa" and "bbb")  //error
    println()

    ////////////////////////////////////////////////////////////////////////////////////////////////

    //4. 제어문 특이점 ///////////////////////////////////////////////////////////////////////////////
    //제어문의 종류 : if, when, while, for (switch, 삼항연산자 사용불가)

    //4-1. 삼항연산자 --> if 문으로 대신함
    //var str= (10>5)? "Hello":"Nice" //error (삼항연산자 X)
    var str= if(10>5) "Hello" else "Nice"   //if 문이 마치 연산자 형태로 쓰이며 결과를 줌 (Java 에서는 리턴값이랄게 없었음)

    //** if 문 안에 실행문이 여러 개일 경우, 마지막 값이 결과값이 된다
    str= if(10<5){
        "zzz"
        "aaaa"  //이 문자열만 출력됨
    }else{
        "www"
        println("else 문....")   //실행문 실행시키고
        "bbbb"  //str 가 마지막으로 대입된 결과값을 가짐
    }
    println(str)
    //** 이러한 특성 때문에, 코틀린에서는 if 문 --> if 표현식 이라고 부름 **

    //4-2. switch 문법이 없음 --> 업그레이드 when 으로 대신함
    var h:Any? = null
    //switch(h){}   //error (switch 문 X)

    h= "Hello"
    when(h){
        10-> println("aaa")
        20-> println("bbb")
        "Hello"-> println("Hello")
        true-> println("true")
        n-> println("n 변수값과 같음")
        30,40-> println("30 or 40 입니다")
        else-> {    //default 상태일 경우 else 문 + 여러 줄의 실행문을 사용할 때 {}로 영역 지정
            println("ccc")
            println("ddd")
        }
    }//switch-case 문은 같은 자료형으로만 조건부 작성 가능했었음 --> Any 타입이면 when 문에 여러 자료형 사용 가능

    //when 역시 if 처럼 "표현식" 이므로, 결과값 대입이 가능하다!
    h= 20
    var result= when(h){
        10-> "Hello"
        20-> "Nice"
        else->{
            println("else...")
            "BAD"
        }
    }
    println(result)

    //when 을 특정 수식으로 제어하고 싶다면
    var score= 95
    when{
        //score>=90 && score<=100-> println("A학점 입니다.")
        score in 90..100-> println("A학점 입니다.")  //.. 은 범위 연산자 (90, 100 포함)
        score>=80-> println("B학점 입니다.")
        score>=70-> println("C학점 입니다.")
        score>=80-> println("D학점 입니다.")
        else-> println("F학점 입니다.")
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    //5. 반복문 특이점 ///////////////////////////////////////////////////////////////////////////////
    //while 문은 그대로

    //for 문은 작성문법이 완전히 달라짐
    //for(var i=0; i<5; i++){}    //기존 for 문

    //0부터 5까지 6번 반복하는 반복문
    for(i in 0..5){
        println(i)
    }
    println()
    for(a in 3..10){
        println(a)
    }
    println()
    //마지막 번호(ex 배열의 방번호) 빼고 반복하기
    for(n in 0 until 10) println(n)
    println()
    //2칸씩 증가시키기
    for(m in 0..10 step 2) println(m)
    println()
    //값을 감소시키기
    for(l in 10 downTo 0 step 3) println(l)
    println()
    //break, continue 문법은 동일
    //* 권장하지는 않으나, break 에 의해 멈추는 반복문을 선택 가능
    //기본 for 문에서의 break
    for(n in 0..5){
        if(n==3) break
        print("$n  ")
    }
    println()

    //중첩 for 문에서의 break
    for(y in 0..5){
        print("$y : ")
        for(x in 0..10){
            if(x==6) break
            print("$x  ")
        }
        println()
    }

    //** @Label 을 통해 break 될 반복문 선택 (break 가 어떤 문장의 소속인지 지정 가능) **
    KKK@ for(y in 0..5){
        print("$y : ")
        for(x in 0..10){
            if(x==6) break@KKK
            print("$x  ")
        }
        println()
    }
}