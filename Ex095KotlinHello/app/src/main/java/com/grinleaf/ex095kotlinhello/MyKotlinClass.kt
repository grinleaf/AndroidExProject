package com.grinleaf.ex095kotlinhello

//자바와 다르게 클래스명-파일명이 반드시 같을 필요는 없음! + 패키지명도 동일할 필요는 없음(접근제한자 범위만 달라짐)
class MyKotlinClass {
    val a:Int= 20

    fun show(){
        println("MyKotlin show : $a")
        println()
    }
}