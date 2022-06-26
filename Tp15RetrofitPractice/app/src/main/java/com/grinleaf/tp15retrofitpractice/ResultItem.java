package com.grinleaf.tp15retrofitpractice;

import java.util.ArrayList;

public class ResultItem {
    ArrayList<BodyItem> body;       //"body":[{}] --> 요건 배열 형태의 json 이라 ArrayList 로 받음
    HeaderItem header;              //"header":{} --> 얘는 고냥 객체 하나!
}

class BodyItem{
    String TIME;    //시간 : 2022-03-31 16:00
    double SO2;     //이산화황
    int MINU;       //미세먼지
    double OZ;      //오존
    double NO2;     //이산화질소
    double CMO;     //일산화탄소
    int ULFPTC;     //초미세먼지
}

class HeaderItem{
    int perPage;
    String resultCode;
    int totalRows;
    int currentPage;
    String resultMsg;
}
