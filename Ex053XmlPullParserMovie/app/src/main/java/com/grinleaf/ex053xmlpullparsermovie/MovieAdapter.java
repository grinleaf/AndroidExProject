package com.grinleaf.ex053xmlpullparsermovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.VH> {
    Context context;
    ArrayList<Item> items;

    public MovieAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);

        return new VH(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Item item= items.get(position);
        holder.rank.setText(item.rank);
        holder.name.setText(item.name);
        holder.openDt.setText(item.openDt);
        holder.audiAcc.setText(item.audiAcc);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //inner class
    class VH extends RecyclerView.ViewHolder{

        TextView rank;
        TextView name;
        TextView openDt;
        TextView audiAcc;

        public VH(@NonNull View itemView) {
            super(itemView);
            rank= itemView.findViewById(R.id.tv_rank);
            name= itemView.findViewById(R.id.tv_name);
            openDt= itemView.findViewById(R.id.tv_open);
            audiAcc= itemView.findViewById(R.id.tv_audi);
        }
    }
}
