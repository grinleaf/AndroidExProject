package com.grinleaf.ex031viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

    //2
    Context context;
    ArrayList<Integer> imgIds;

    //3. alt+insert 생성자 추가 --> 2의 변수 사용
    public MyAdapter(Context context, ArrayList<Integer> imgIds) {
        this.context = context;
        this.imgIds = imgIds;
    }

    //4. 자동완성 후 작성
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //4-1. view 를 객체로. inflater
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView= inflater.inflate(R.layout.page,parent,false);
        
        //4-2. inflate 처리한 itemView 를 ViewHolder 로 참조변수 저장
        VH vh= new VH(itemView);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.iv.setImageResource(imgIds.get(position));
    }

    @Override
    public int getItemCount() {
        return imgIds.size();
    }

    //1. 이너클래스(ViewHolder) : 아이템 1개뷰의 참조변수 저장 목적
    class VH extends RecyclerView.ViewHolder{

        ImageView iv;
        public VH(@NonNull View itemView) {
            super(itemView);

            iv= itemView.findViewById(R.id.iv);
        }
    }
}
