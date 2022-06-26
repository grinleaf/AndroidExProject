package com.grinleaf.ex091firebasechatting;

//10. 리사이클러뷰에 쓰일 데이터 클래스
public class MessageItem {
    public String name;
    public String message;
    public String time;
    public String profileUrl;

    public MessageItem(String name, String message, String time, String profileUrl) {
        this.name = name;
        this.message = message;
        this.time = time;
        this.profileUrl = profileUrl;
    }

    public MessageItem() { }
}
