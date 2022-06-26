package com.grinleaf.ex087retrofit2marketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.grinleaf.ex087retrofit2marketapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    ArrayList<ItemVO> items= new ArrayList<>();
    MarketAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter= new MarketAdapter(this, items);
        binding.recycler.setAdapter(adapter);
        //리사이클러 뷰에 구분선 꾸미기(ItemDecoration - DividerItemDecoration)
        binding.recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        binding.btnEdit.setOnClickListener(v->{
            //1. 글작성 화면(Activity)로 전환
            Intent intent= new Intent(this,EditActivity.class);
            startActivity(intent);
        });

        //0. 동적퍼미션
        String[] permissions= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(checkSelfPermission(permissions[0])== PackageManager.PERMISSION_DENIED)
            requestPermissions(permissions,0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "외부저장소를 허용하셨습니다.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "글쓰기가 금지됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    //액티비티가 화면에 보여질 때 : [ onResume() ]
    @Override
    protected void onResume() {
        super.onResume();

        loadData();
    }

    void loadData(){

        binding.pb.setVisibility(View.VISIBLE);

        //서버에서 데이터 불러오는 작업
        Retrofit retrofit= RetrofitHelper.getRetrofitInstanceGson();
        RetrofitService retrofitService= retrofit.create(RetrofitService.class);
        Call<ArrayList<ItemVO>> call= retrofitService.loadDataFromServer();

        call.enqueue(new Callback<ArrayList<ItemVO>>() {
            @Override
            public void onResponse(Call<ArrayList<ItemVO>> call, Response<ArrayList<ItemVO>> response) {

                items.clear();  //껐다켰을 때 원래 add 된 뷰 + db 에 저장된 데이터이 다시 로드되어 add 되므로, 원래 뷰에 있던 데이터들은 날려줘야함
                adapter.notifyDataSetChanged();

//                items= response.body(); //adapter 에서 뷰에 연결된 데이터 items 에 대입하기
//                adapter.notifyDataSetChanged();   //adapter 에게 데이터 변경을 알림 --> 요건 데이터 양이 많아질수록 로드가 오래걸린댕 (아예 db 를 첨부터 다시 불러오기 때문에,,_
                ArrayList<ItemVO> list= response.body();
                for(ItemVO item:list){
                    items.add(0,item);  //새로 받아오는 데이터를 윗쪽에 추가하기
                    adapter.notifyItemInserted(0);  //어댑터가 데이터를 처음부터 끝까지 전부 건드리지 않고, 0번방에 있는 아이템이 insert 되었는지만 확인함
                }

                binding.pb.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<ItemVO>> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}