package com.grinleaf.ex095kotlinhello

fun main(){
    //7. 함수 function //////////////////////////////////////////////////////////////////////////////
    show()
    output(100, "Hello")
    var sum= sum(50,30)
    println(sum)
    println()

    //4)-1. 리턴이 없는 함수의 리턴도 받을 수 있음
    var x= display()
    println(x)  //Unit 자료형
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //5) 함수 선언의 단순화 ★★ ///////////////////////////////////////////////////////////////////////
    //   리턴 키워드를 할당 연산자(=)로 바꿀 수 있음
    var data= getData()
    println(data)
    var data2= getData2()
    println(data2)
    var data3= getData3(5)
    println(data3)
    var data4= getData3(15)
    println(data4)
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //6) 익명함수 [ 이름이 없는 함수 ] ★★ /////////////////////////////////////////////////////////////
    //6)-8  //////////////////////////
    aaa()   //기존함수 호출
    bbb()   //익명함수 호출
    ccc()
    ddd()
    eee()
    fff()
    ggg("Android")
    hhh("ios")
    iii("hybrid")
    jjj("Nice")
    kkk("Good")
    lll("sam", 20)

    //6)-9  ///////////////////////////
    val m= mmm()
    println(m)
    println(nnn())
    println(ooo())
    println(ppp())
    println(qqq(10,20))
    println(rrr(30,40))
    println(stringLength("Hello"))

    //함수를 변수에 대입할 수 있으며, 그 변수를 다시 다른 변수에 대입도 가능
    val aaaaa:(String)->Int= stringLength   //참조변수의 자료형을 생략해도 자동추론됨!
    aaaaa("kkk")
    println()

    //7) [ 고차함수 ] : 함수의 파라미터로 다른 함수를 전달받는 함수
    sss(100)
    ttt(10,sss)

    www("Android", stringLength)    //문자열(String)을 받아 해당 문자열의 길이(Int)를 리턴해주는 함수
    www("ios",{str->str.length+1})    //즉석에서 익명함수를 만들어 기능을 커스텀 가능~! 연산도 할 수 있고..
    www("456",{s->s.toInt()})
    
    //8) 함수 파라미터의 default value
    xxx(10,20)
    xxx()
    yyy(5,3)
    yyy(b=50)   //첫번째 파라미터를 비우고, 두번째 파라미터에만 값을 주고싶을 때
    zzz("Hello",100)
    zzz(b=200,a="Nice") //ㅋㅋ 되긴하는데 웬만하면 순서 지켜서 써라~

}//main 함수...

//8)-1. 함수 파라미터의 default value
fun xxx(a:Int=0, b:Int=1000){   //미리 값을 넣어두면, 디폴트 값으로 설정되어 값을 전달받지 못했을 때 해당 값 출력
    println(a)
    println(b)
    println()
}

//fun yyy(a:Int, b:Int=1000){   //첫번째 파라미터는 값을 받고, 두번째 파라미터는 디폴트값을 주기
//    println(a)
//    println(b)
//    println()
//}

fun yyy(a:Int=0, b:Int){        //첫번째 파라미터는 디폴트값을 주고, 두번째 파라미터는 값을 받아오기 (표기법 위에!)
    println(a)
    println(b)
    println()
}

fun zzz(a:String, b:Int){
    println(a)
    println(b)
    println()
}


//7)-1. 고차 함수
val sss= fun(a:Int){
    println("sss: $a")
}

val ttt= fun(num:Int, kkk:(Int)->Unit){ //(Int , Int 를 받아서 Unit 을 리턴하는 함수를 참조)
    println("num: $num")
    kkk(500)    //요기서 부른 kkk()는 사실상 sss() 와 같음 --> 함수를 전달해주는 것!
}

val www= fun(str:String, aaa:(String)->Int){
    val a= aaa(str)
    println("결과 : $a")
}


//6)-1. 기존 함수
fun aaa(){
    println("aaa")
}

//6)-2. 익명 함수 : 함수 이름을 지정하지 않음. 함수 이름으로 호출 불가 --> 함수를 참조하는 참조변수에 대입 (객체처럼 취급!)
var bbb= fun(){     //함수를 참조하는 변수 bbb 가 함수의 이름이 됨
   println("bbb")
}

//6)-3. 익명 함수에 자료형 명시 : 참조변수가 참조하는 함수의 파라미터와 리턴타입을 람다식으로 표현
var ccc:()->Unit= fun(){    //ccc 가 Unit 값(return 이 없는 함수의 기본 리턴타입은 Unit)을 리턴하는 함수()를 참조함
    println("ccc")
}

//6)-4. 익명 함수 표기 단축법 ① 단계 : 앞에서 이미 참조하는 함수의 정보를 다 표기해줬으니...뒷부분에선 아예 생략해버리기 ㅇㅅㅇ
var ddd:()->Unit= {
    println()
}

//6)-5. 익명 함수 표기 단축법 ② 단계 : {} 안에 실행문을 넣고 참조시키면, 자동추론으로 인해 익명함수 참조변수가 됨!
var eee= {
    println("eee")
}

//6)-6. 주의점 : 익명함수와 구별 - 요건 println()의 실행 결과값을 가지는 변수 fff 임. 중괄호까지는 생략 불가
//var fff= println("fff")
//** 중괄호 생략이 안되는 이유 **
//- 해석의 여지가 두 가지로 갈리기 때문 : 함수를 참조하고 있는지, 해당 함수의 리턴값을 참조하고 있는지 정확히 알 수 없음

//6)-7. 익명함수를 다른 참조변수에 대입 가능 (함수를 객체처럼 저장)
var fff= eee

//6)-8. 파라미터 있는 익명함수 ///////////////////////////////////////////////////////////////////////////////////////////////////
var ggg= fun(s:String){
    println("글자수 : ${s.length}")
}

var hhh:(String)->Unit= fun(s:String){  //hhh 참조변수는 파라미터로 String 값을 받는 함수를 참조함
    println("글자수 : ${s.length}")
}

var iii:(String)->Unit= {   //hhh 변수의 fun(s:String) 을 람다식 s-> 으로 축약 --> 엄밀히 말하면, {} 안으로 파라미터가 들어온 것 {s-> ...}
    s-> println("글자수 : ${s.length}")
}

var jjj:(String)->Unit= {
    //생략한 파라미터는 자동으로 it 이라는 키워드로 참조됨
    println("글자수 : ${it.length}")
}

var kkk= {
    //참조변수의 리턴타입을 자동 타입추론 시키기 위한 최최최종 축약법 : 파라미터를 {} 안에 명시
    s:String-> println("글자수 : ${s.length}")
}

var lll:(String, Int)->Unit={  //매개변수 여러 개 축약형
    name, age-> println("$name : $age")
}
////////////////////////////////////////////////////////////////////////////////////////////////////////

//6)-9. 리턴이 있는 익명함수 ///////////////////////////////////////////////////////////////////////////////
var mmm= fun():Int{
    return 10
}

var nnn:()->Int= fun():Int{     //타입 명시
    return 10
}

var ooo:()->Int= {  //뒷변 타입 생략 --> return 키워드도 생략해야함 (=람다식 표기 특성~!)
    30
}
var ppp:()->Int= {  //마지막 실행문이 리턴값으로 나옴! 편집기에서 결과값으로 들어갈 값 옆에 알려줌 ^_^
    40
    println("ppppppp")  //error X (얘는 리턴값이 아니니 정상적으로 실행됨)
    50
    60
    //println("ppppppp")  //error (얘는 마지막에 와서 리턴값이니 Int 가 아닌 Unit 값임)
}

var qqq:(Int, Int)-> Int= fun(a:Int, b:Int):Int{    //여러 개의 파라미터 o, 리턴타입 o 기본형식
    return a+b
}

var rrr:(Int, Int)->Int={   //qqq()의 람다식 표기
    a,b -> a+b
}

var stringLength:(String)->Int={s:String->s.length}     //위의 축약방식 적용하여 만든 문자열의 길이를 알려주는 함수
/////////////////////////////////////////////////////////////////////////////////////////////////////////

//5)-1. 기존 함수 사용법
fun getData():String{
    return "Hello"
}

//5)-2. 함수 선언의 단순화 - 간단한 함수
fun getData2():String = "Hello"

//5)-3. 함수 선언의 단순화 - 복잡한 함수
fun getData3(num:Int):String= if(num<0) "Good" else "Bad"


//4) 리턴이 없는 함수 : 리턴타입은 존재. 생략된 상태 (=void 같은 역할) [ :unit 자료형 ]
fun display(){
    println("display")
}


//3) 값을 리턴하는 함수 : 리턴타입 명시 위치 - 함수명() 다음에 타입 명시함 [ 함수명():리턴타입 ] --> 변수 자료형 표기법과 동일
fun sum(a:Int, b:Int):Int{
    return a+b
}


//2) 파라미터를 전달하는 함수 : var, val 키워드 사용 X (무조건 val 로 선언됨 =상수 취급. 값 변경 코드 기입 시 error)
fun output(a:Int, b:String){
    println(a)
    println(b)
    //b= "Nice"   //error (매개변수들은 val = 상수)
    println()
}


//1) 코틀린에서의 함수 : 함수를 정의할 때 리턴타입 위치에 fun 키워드 사용
//   (함수/메소드 명칭 상황에 따라 사용 - 클래스 내에 멤버변수로 있을경우 (=메소드) / main() 바깥에 선언한 기능함수 (=함수)
fun show(){
    println("show function")
    println()
}