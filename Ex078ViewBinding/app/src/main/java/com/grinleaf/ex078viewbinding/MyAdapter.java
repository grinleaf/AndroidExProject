package com.grinleaf.ex078viewbinding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grinleaf.ex078viewbinding.databinding.RecyclerItemBinding;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

    Context context;
    ArrayList<Item> items;

    public MyAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
//        Item item= items.get(position);
//        holder.binding.tv.setText(item.title);
//        holder.binding.iv.setImageResource(item.imgId);
        holder.bindItem(items.get(position));   //VH 클래스 안의 bindItem() 메소드로 깔끔하게 정리~
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        //9. recycler_item.xml 과 연결을 담당하는 바인딩클래스가 자동으로 존재하게 됨 (xml 문서의 이름을 기반으로 클래스 이름 만들어짐 (recycler_item -> RecyclerItem)
        RecyclerItemBinding binding;

        public VH(@NonNull View itemView) {
            super(itemView);
            binding= RecyclerItemBinding.bind(itemView); //이미 inflate 를 한 상태인 뷰와 연결하려면 .inflate() 가 아니라 .bind() 를 쓴다!
        }

        void bindItem(Item item){
            binding.tv.setText(item.title);
            binding.iv.setImageResource(item.imgId);
        }
    }
}
