package com.grinleaf.ex095kotlinhello

import android.Manifest

fun main(){
    //6. 배열 Array & 컬렉션 Collection //////////////////////////////////////////////////////////////
    //6-1. 배열 : 요소 개수의 변경이 불가능한 배열 (Array) - 변수 선언 시 [] 키워드 사용하지 않음
    //var arr:int[] //error ([] 사용 X)
    //var arr:Array<Int> = arrayOf(10,20,30)   //배열만드는 함수를 이용 : [ arrayOf() ] //<> 뒤에는 바로 = 이 올 수 없음. 공백 필요
    var arr= arrayOf(10,20,30)  //요런 식으로 다 생략하고 타입추론해서 사용

    //요소 값 출력
    println(arr[0])
    println(arr[1])
    println(arr[2])
    //println(arr[3]) //Exception 발생

    //요소 값 변경
    arr[0]= 100
    println(arr[0])
    println()

    //특이점 : 코틀린의 모든 변수는 객체 참조변수 / 모든 변수는 객체 --> 멤버 메소드 보유
    //        배열 역시 객체이므로 요소 값을 변경하는 메소드를 가짐
    arr.set(1,200)      //1번 방의 값을 200으로 변경
    println(arr.get(1)) //get() 함수로 요소 값 가져오기 --> ArrayList<>() 처럼 get, set 보유 but, 코틀린은 [] 사용 권장

    //배열의 길이값
    println("요소의 개수 : ${arr.size}") //"" 안에서 객체의 메소드 사용 시 {} 써야함

    //배열 요소 값 출력을 일일이 하지않고 반복문 이용하기
    for(i in 0 until 3){
        println(arr[i])
    }
    println()

    //인덱스 번호로 제어하지 않고, 배열요소값을 바로 반복하면 인덱스번호를 가지고 옴과 동시에 처리 가능 (=for each 문)
    for(n in arr){
        println(n)  //n 은 요소값 - 인덱스 x
    }
    println()

    //배열의 인덱스 번호 추출 : [ .indices ]
    for(i in arr.indices){
        println(i)  //i 는 인덱스 번호
    }
    println()

    //인덱스와 값을 둘다 받아 처리하기 : [ .withIndex() ]
    for((i,v) in arr.withIndex()){  //두 개 이상의 변수를 함께 쓸 때 (인덱스번호, 값) 필요
        println("$i : $v")
    }
    println()

    //** 배열 객체의 멤버에 foreach 기능메소드 존재 : 함수형 프로그래밍 언어의 배열에 공통으로 있는 기능
    //배열 요소의 각각에 반복적으로 접근할 때마다 {}코드가 실행되도록 하는 foreach 기능메소드
    //forEach 의 {} 안에는 생략된 임시변수 it 이 있고, it 은 곧 배열요소의 값을 가짐
    arr.forEach {
        println(it)     //임시변수 it
    }
    println()

    //배열을 만들 때 자료형을 명시하여 같은 자료형만 저장하게 할 수 있음
    //var arr2= arrayOf(10,true,"Hello")  //이렇게 만들면 Any 타입으로 배열요소 만들어짐
    //var arr2= arrayOf<Int>(10,20,true)  //error (true 값은 Int 배열에 들어갈수 x)
    var arr2= arrayOf<Int>(10,20,30)
    //<>제네릭 표기 간소화
    var arr3= intArrayOf(10,20,30)
    var arr4= doubleArrayOf(3.14,5.55,4.21)
    //기초데이터타입마다 xxxArrayOf() 형태 존재! 참조타입은 없음
    //var arr5= stringArrayOf() //error (참조타입 ArrayOf() X)

    //배열의 참조변수의 자료형을 먼저 명시해보기 [ 특정 자료형의 배열타입 ]
    var arr5:IntArray= intArrayOf(10,20,30)

    //배열은 기본적으로는 null 을 저장하지 않으나, 배열요소의 시작값을 null 로 하고 싶을 경우 : [ arrayOfNulls<>() ]
    var arr6= arrayOfNulls<Double>(5)   //5개의 null
    for(t in arr6) println(t)
    println()

    //배열 참조변수에 자동추론을 하지 않고 자료형 명시 --> null 이 가능한 자료형으로 명시할 것
    var arr7:Array<Float?> = arrayOfNulls<Float>(3) //앞에서 <> 표시 했을 경우 뒤의 제네릭은 생략해도 상관 없음 (무시됨)
    var permissions= arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)  //앞으론 요렇게 쓸 것

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //6-2. 자바의 Collection 과 같은 기능 클래스들 <-- 배열은 한번 만들어지면 요소 개수 변경이 불가능 하나, Collection 은 요소 개수가 동적임
    //1) List : 순서대로 저장, 인덱스번호 자동부여, 중복 데이터 O
    //2) Set : 순서대로 저장 X, 인덱스번호 X, 중복 데이터 X
    //3) Map : 순서대로 저장 X, [키, 밸류] 쌍으로 요소가 저장, key 는 중복 X, value 는 중복 O

    //코틀린의 Collections 들은 모두 요소의 추가/삭제 및 변경이 불가능한 종류와 가능한 종류로 나뉨
    //6-2-1. 요소의 개수 추가/삭제/변경이 불가능한 컬렉션 : listOf(), setOf(), mapOf()
    //6-2-2. 요소의 개수 추가/삭제/변경이 가능한 mutable 컬렉션 : mutableListOf(), mutableSetOf(), mutableMapOf()

    //6-2-1. 요소의 변경이 불가능한 컬렉션
    //1)List
    val list:List<Int> = listOf(10,20,30)
    for(i in list) println(i)
    println()

    //값의 추가/삭제/변경에 관련된 기능메소드가 없음
    //list.add()      //error
    //list.remove()   //error
    //list.set()      //error

    //배열보다 더 엄격한 배열 : 요소 개수 변경 및 값 변경도 안됨!
    //2) Set
    val set:Set<Double> = setOf(3.14,5.55,2.22,3.14)   //중복 데이터 실행 X
    for(e in set) println(e)
    println()

    //3) Map
    //3)-1. Pair 객체 이용
    val map:Map<String, String> = mapOf(Pair("title","Hello"), Pair("msg","Nice to meet you,"))
    println("요소개수 : ${map.size}")
    for((key, value) in map){
        println("$key : $value")
    }
    println()

    //3)-2. to 키워드 이용
    val map2:Map<String, String> = mapOf("id" to "mrhi", "pw" to "1234")
    println("요소개수 : ${map2.size}")
    for((key, value) in map2){
        println("$key : $value")
    }

    //6-2-2. 요소의 추가/삭제/변경이 가능한 mutable 컬렉션들
    //1) MutableList
    val aaa:MutableList<Int> = mutableListOf(10,20,30)
    println("요소개수 : ${aaa.size}")
    aaa.add(40)
    aaa.add(0,50)
    println("요소개수 : ${aaa.size}")
    //aaa.set(1,200)  //특정 위치 값 변경 가능 (권장 x)
    aaa[1]= 200 //권장 o
    for(e in aaa) println(e)
    println()

    //2) MutableSet
    val bbb:MutableSet<Double> = mutableSetOf()
    bbb.add(5.55)
    bbb.add(3.14)
    bbb.add(5.55)   //중복데이터는 자동으로 무시됨
    for(e in bbb) println(e)
    println()

    //3) MutableMap
    val ccc:MutableMap<String, String> = mutableMapOf(Pair("name","sam"), "tel" to "010-1234-5678")
    ccc.put("address", "seoul")
    for((k,v) in ccc) println("$k : $v")
    println()

    //협업자가 배열이 아닌 컬렉션을 사용했다면 --> 요소의 변경을 의도 (대부분 mutable collection 사용)
}
