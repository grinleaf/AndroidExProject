package com.grinleaf.tp15retrofitpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.grinleaf.tp15retrofitpractice.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn1.setOnClickListener(v->clickbtn1());
    }

    void clickbtn1(){

        //1. Retrofit 객체 생성
        Retrofit.Builder builder= new Retrofit.Builder();
        builder.baseUrl("http://apis.data.go.kr");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();

        //2. Retrofit 이 구현하는 인터페이스 설계 --> RetrofitService

        //3. RetrofitService 의 규격에 맞춘 Retrofit 객체 생성 --> 요기가 implement 단계인거?
        RetrofitService retrofitService= retrofit.create(RetrofitService.class);

        //4. API 에서 제공되는 json 객체의 메소드를 실행하는 Call 객체(Retrofit 기능) 생성
        String key= "GAYV2qaMCK8H11gfq8gVGXUR%2FDnjO9NHQv9aB%2Fwzy6G13QIoH9C6BGR3P7RFCXLjEXwcUPAQ%2FtOxiPXJLmJceQ%3D%3D";
        int page= 1; //현재 페이지 번호
        int perPage= 10;    //한 페이지 결과 수
        int code= 533112;

        Call<ResultItem> call= retrofitService.getRealtimeStandbyInfo(key, page+"", perPage+"", code+"");

        //5. 네트워크 작업 수행
        call.enqueue(new Callback<ResultItem>() {
            @Override
            public void onResponse(Call<ResultItem> call, Response<ResultItem> response) {
                ResultItem resultItem= response.body();     //모든 데이터(body, header)를 받을 객체
                ArrayList<BodyItem> items= resultItem.body; //서버에서 받아온 모든 데이터 중 ArrayList<BodyItem> body 객체를 items 에 대입

                settingTv(items);
            }

            @Override
            public void onFailure(Call<ResultItem> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Retrofit failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void settingTv(ArrayList<BodyItem> items){
        binding.tvTime.setText("시간 : "+items.get(0).TIME);
        binding.tvSo2.setText("이산화황 : "+items.get(0).SO2);
        binding.tvMinu.setText("미세먼지 : "+items.get(0).MINU);
        binding.tvOz.setText("오존 : "+items.get(0).OZ);
        binding.tvNo2.setText("이산화질소 : "+items.get(0).NO2);
        binding.tvCmo.setText("일산화탄소 : "+items.get(0).CMO);
        binding.tvUlfptc.setText("초미세먼지 : "+items.get(0).ULFPTC);
    }
}