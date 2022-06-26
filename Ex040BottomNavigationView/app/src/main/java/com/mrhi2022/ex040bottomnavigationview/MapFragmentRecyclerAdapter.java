package com.mrhi2022.ex040bottomnavigationview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MapFragmentRecyclerAdapter extends RecyclerView.Adapter<MapFragmentRecyclerAdapter.VH> {

    Context context;
    ArrayList<MapFragmentRecyclerItem> items;

    public MapFragmentRecyclerAdapter(Context context, ArrayList<MapFragmentRecyclerItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.mapfragment_recycler_item, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        MapFragmentRecyclerItem item= items.get(position);

//        holder.tvTitle.setText(item.title);
//        holder.tvMessage.setText(item.message);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{
//        ImageView iv;
//        TextView tvTitle;
//        TextView tvMessage;
        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
