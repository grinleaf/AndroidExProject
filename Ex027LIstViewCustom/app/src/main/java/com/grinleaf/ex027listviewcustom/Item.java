package com.grinleaf.ex027listviewcustom;

//대량의 데이터 1개 아이템의 데이터를 저장할 객체 설계도면
public class Item {
    
    String name;    //이름
    String nation;  //국적
    int imgId;      //R.drawable.korea = 이미지의 식별자
 
    //이 클래스를 객체 생성(new)할 때 자동으로 실행되는 메소드
    //생성자 메소드 : constructor
    public Item(String name, String nation, int imgId){
        //파라미터로 받은 데이터들을 멤버변수로 대입
        this.name= name;
        this.nation= nation;
        this.imgId= imgId;
    }
    
}
