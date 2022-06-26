package com.grinleaf.ex053xmlpullparsermovie;

public class Item {
    String rank;        //순위
    String name;        //영화제목
    String openDt;      //개봉일
    String audiAcc;     //누적관객수

    public Item(String rank, String name, String openDt, String audiAcc) {
        this.rank = rank;
        this.name = name;
        this.openDt = openDt;
        this.audiAcc = audiAcc;
    }

    public Item() {}
}
