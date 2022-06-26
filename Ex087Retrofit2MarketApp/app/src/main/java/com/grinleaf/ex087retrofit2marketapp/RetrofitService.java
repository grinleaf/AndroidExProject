package com.grinleaf.ex087retrofit2marketapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface RetrofitService {

//    @FormUrlEncoded   //@Multipart 과 @FormUrlEncoded 는 양립 불가. --> json 의 encoded 타입 설정 시 둘 중 하나만 선택할 수 있기 때문에 !
    @Multipart
    @POST("05Retrofit/insertDB.php")
    Call<String> postDataToServer(@PartMap Map<String, String> dataPart, @Part MultipartBody.Part filePart);
    //MultiPartBody.Part --> 데이터만 들어있는 Part 인 dataPart 와 파일만 들어있는 Part 인 filePart 를 따로 포장
    //이 때, dataPart 는 @PartMap 으로(Map 방식은 HashMap 클래스 이용), filePart 는 @Part 로 만든당

    @GET("05Retrofit/loadDB.php")
    Call<ArrayList<ItemVO>> loadDataFromServer();  //데이터 보낼 거 아니고 받아오기만 할거니까 파라미터에 암것도 안주면 된다고~~~~~~~~
}
