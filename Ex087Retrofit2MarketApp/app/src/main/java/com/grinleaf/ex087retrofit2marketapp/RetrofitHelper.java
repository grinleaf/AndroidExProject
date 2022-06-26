package com.grinleaf.ex087retrofit2marketapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitHelper {

    static String baseUrl= "http://grinleaf.dothome.co.kr"; //static 안에서는 static 변수만 사용가능

    public static Retrofit getRetrofitInstanceGson(){
        Retrofit.Builder builder= new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        return retrofit;
    }

    public static Retrofit getRetrofitInstanceScalars(){
        Retrofit retrofit= new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(ScalarsConverterFactory.create()).build();
        return retrofit;
    }
}

