package com.grinleaf.tp07carrotmarket;

public class Trade {

    int imgId;
    String tradeTitle;
    String tradePlace;
    String tradeCharge;
    int bubbleCount;
    int heartCount;

    public Trade(int imgId, String tradeTitle, String tradePlace, String tradeCharge, int bubbleCount, int heartCount) {
        this.imgId = imgId;
        this.tradeTitle = tradeTitle;
        this.tradePlace = tradePlace;
        this.tradeCharge = tradeCharge;
        this.bubbleCount = bubbleCount;
        this.heartCount = heartCount;
    }
}
