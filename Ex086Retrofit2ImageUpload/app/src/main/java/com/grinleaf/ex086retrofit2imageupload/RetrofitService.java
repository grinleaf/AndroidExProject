package com.grinleaf.ex086retrofit2imageupload;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitService {

    //4-3-c. 인터페이스 설계
    @Multipart
    @POST("05Retrofit/fileUpload.php")
        //문자열이 아닌 file 보내기 위해서는 패킹 클래스 필요 : [ Part ]    //문자열은 GET-@Query , POST-@Field 였음~~
    Call<String> uploadImage(@Part MultipartBody.Part file);
}
