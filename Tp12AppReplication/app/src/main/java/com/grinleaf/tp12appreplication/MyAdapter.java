package com.grinleaf.tp12appreplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<VH> {

    Context context;
    ArrayList<ToolbarGrid> gridItems;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.grid_toolbar_item,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        VH vh= holder;

        for(int i=0; i<gridItems.size(); i++) {
            vh.iv.setImageResource(R.drawable.ic_baseline_airline_seat_recline_extra_24+i);
            vh.menuName.setText(R.array.toolbar_grid+i);
        }
    }

    @Override
    public int getItemCount() {
        return gridItems.size();
    }
}

class VH extends RecyclerView.ViewHolder{

    ImageView iv;
    TextView menuName;

    public VH(@NonNull View itemView) {
        super(itemView);

        iv= itemView.findViewById(R.id.grid_iv);
        menuName= itemView.findViewById(R.id.grid_tv);

    }
}
