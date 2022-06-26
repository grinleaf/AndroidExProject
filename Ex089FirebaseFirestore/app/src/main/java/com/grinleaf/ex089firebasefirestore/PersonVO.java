package com.grinleaf.ex089firebasefirestore;

public class PersonVO {
    //Firebase 사용 시 접근제한자 public 이어야 error 나지 않음!
    public String name;
    public int age;
    public String address;

    public PersonVO(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public PersonVO() { }
}
