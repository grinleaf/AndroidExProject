package com.grinleaf.ex029recyclerview;

public class Item {
    String name;
    String message;

    // Alt + insert 키 누르면 자동삽입 기능 ㄷㄷㄷㄷㄷㄷㄷㄷㄷ
    public Item(String name, String message) {
        this.name = name;
        this.message = message;
    }

    //가급적 파라미터가 없는 생성자도 오버로딩 해놓을 것
    public Item() {
    }
}
