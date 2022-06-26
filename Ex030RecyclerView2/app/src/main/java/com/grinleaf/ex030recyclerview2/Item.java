package com.grinleaf.ex030recyclerview2;

public class Item {
    String name;
    String message;
    int profileId;      //R.drawable.crew_chopper
    int imageId;        //R.drawable.bg_img01

    public Item(String name, String message, int profileId, int imageId) {
        this.name = name;
        this.message = message;
        this.profileId = profileId;
        this.imageId = imageId;
    }

    public Item() {
    }
}
