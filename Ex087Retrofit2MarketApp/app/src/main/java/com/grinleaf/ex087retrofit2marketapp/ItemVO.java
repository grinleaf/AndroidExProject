package com.grinleaf.ex087retrofit2marketapp;

import com.google.gson.annotations.SerializedName;

//VO클래스 : value object 클래스 - 값만 저장하는 목적의 클래스
public class ItemVO {
    //market 테이블의 컬럼명과 동일해야함 --> 컬럼명이 맘에 들지 않을 때 java 에서 변수명을 변경하는 방법 [ @SerializedName("컬럼명") ]
    int no;
    String name;
    String title;
    @SerializedName("msg")
    String message;
    String price;
    String file;
    String date;

    public ItemVO(int no, String name, String title, String message, String price, String file, String date) {
        this.no = no;
        this.name = name;
        this.title = title;
        this.message = message;
        this.price = price;
        this.file = file;
        this.date = date;
    }

    public ItemVO() { }
}
