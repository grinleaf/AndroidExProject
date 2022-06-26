package com.grinleaf.ex096kotlinrecyclerviewapp

//주생성자의 파라미터에 var 키워드 쓰면 멤버변수로 인식 (멤버변수이므로 해당 클래스의 변수에 파라미터 변수 대입하는 과정 생략, 생성자 insert 도 필요없음!)
//클래스 내용이 데이터를 받는 용도 뿐이라면, {}도 생략해도 됨. 웬만하면 data class 로 선언해주면 좋음!
data class Item constructor(var title:String, var msg:String, var img:Int)