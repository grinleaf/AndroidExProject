package com.grinleaf.tp15retrofitpractice;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("6430000/realtimeStandbyInfoService/getRealtimeStandbyInfo")
    Call<ResultItem> getRealtimeStandbyInfo(@Query("serviceKey") String key, @Query("currentPage") String page,
                                            @Query("perPage") String perPage, @Query("CODE") String code);
    //@Query 가 URL 의 ? 이후 요청변수값 입력하는 과정을 대신해줌! &key= value < 요고
}
