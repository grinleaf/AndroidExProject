package com.mrhi2022.ex031viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

    Context context;
    ArrayList<Integer> imgIds;

    public MyAdapter(Context context, ArrayList<Integer> imgIds) {
        this.context = context;
        this.imgIds = imgIds;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.page, parent, false);
        VH vh= new VH(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.iv.setImageResource( imgIds.get(position) );
    }

    @Override
    public int getItemCount() {
        return imgIds.size();
    }

    //inner class : 아이템 1개뷰의 참조변수들을 저장하는 목적의 클래스
    class VH extends RecyclerView.ViewHolder{
        ImageView iv;
        public VH(@NonNull View itemView) {
            super(itemView);
            iv= itemView.findViewById(R.id.iv);
        }
    }
}
