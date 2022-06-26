package com.grinleaf.ex087retrofit2marketapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.grinleaf.ex087retrofit2marketapp.databinding.RecyclerItemBinding;

import java.util.ArrayList;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.VH> {

    Context context;
    ArrayList<ItemVO> items;

    public MarketAdapter(Context context, ArrayList<ItemVO> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.recycler_item, parent,false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ItemVO item= items.get(position);
        holder.binding.tvTitle.setText(item.title);
        holder.binding.tvMsg.setText(item.message);
        holder.binding.tvPrice.setText(item.price+"원");

        //Glide 의 load()에는 baseUrl(=host 주소)+파일 경로 주소, 즉 완전한 주소를 주어야 이미지를 불러올 수 있음.
        //item.file 의 경우 파일 경로 주소만 있으므로 로드 불가 --> host 주소를 포함한 절대주소 URL 을 만들어서 load()에 부여하기!
        String imgUrl="http://grinleaf.dothome.co.kr/05Retrofit/"+item.file;
        Glide.with(context).load(imgUrl).error(R.drawable.koala).into(holder.binding.iv);


//        Log.i("imgUrl",imgUrl);


        holder.binding.tgFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //두번째 파라미터 : checked 상태를 가진 변수 (on : true/off : false)
                if(b){
                    //실무에선 db 에 해당 상태를 저장하는 코드도 써야하지만, 로그인 정보가 없으니까 ㅇㅅㅇ...일단 확인만
                    //어떤 name 의 사람마다 게시물마다 좋아요 on/off 상태를 눌렀는지 저장하는 db 를 따로 생성해서 관리해야함
                    Toast.makeText(context, item.title+"관심이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "관심이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        RecyclerItemBinding binding;

        public VH(@NonNull View itemView) {
            super(itemView);
            binding= RecyclerItemBinding.bind(itemView);
        }
    }
}
