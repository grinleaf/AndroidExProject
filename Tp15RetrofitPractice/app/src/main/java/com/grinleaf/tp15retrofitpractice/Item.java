package com.grinleaf.tp15retrofitpractice;

//서버의 json 문서에서 가져와 쓸 데이터 집합 (응답 메시지 항목 中)
public class Item {
//    int perPage;        //필: 한페이지 결과 수
//    int resultCode;     //필: 결과코드
//    int totalRows;      //필: 데이터 총 개수
//    int currentPage;    //필: 페이지번호
//    String resultMsg;   //필: 결과메시지
    String TIME;    //시간 : 2022-03-31 16:00
    double SO2;     //이산화황
    int MINU;       //미세먼지
    double OZ;      //오존
    double NO2;     //이산화질소
    double CMO;     //일산화탄소
    int ULFPTC;     //초미세먼지

    public Item(String TIME, double SO2, int MINU, double OZ, double NO2, double CMO, int ULFPTC) {
        this.TIME = TIME;
        this.SO2 = SO2;
        this.MINU = MINU;
        this.OZ = OZ;
        this.NO2 = NO2;
        this.CMO = CMO;
        this.ULFPTC = ULFPTC;
    }

    public Item() { }
}
