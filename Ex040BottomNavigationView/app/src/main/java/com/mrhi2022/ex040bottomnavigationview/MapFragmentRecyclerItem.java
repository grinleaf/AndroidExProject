package com.mrhi2022.ex040bottomnavigationview;

public class MapFragmentRecyclerItem {
    String title;    //제목
    String message;  //메세지
    String imgUrl;   //이미지 url 주소

    public MapFragmentRecyclerItem(String title, String message, String imgUrl) {
        this.title = title;
        this.message = message;
        this.imgUrl = imgUrl;
    }

    public MapFragmentRecyclerItem() {
    }
}
