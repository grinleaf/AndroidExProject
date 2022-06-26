package com.grinleaf.tp07carrotmarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<VH> {

    Context context;
    ArrayList<Trade> trades;

    public MyAdapter(Context context, ArrayList<Trade> trades) {
        this.context = context;
        this.trades = trades;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView= inflater.inflate(R.layout.trade_view, parent,false);

        VH holder= new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        VH vh= holder;

        Trade trade= trades.get(position);
        vh.ivTrade.setImageResource(trade.imgId);
        vh.tvTitle.setText(trade.tradeTitle);
        vh.tvPlace.setText(trade.tradePlace);
        vh.tvCharge.setText(trade.tradeCharge);
        vh.tvBubbleCount.setText(trade.bubbleCount);
        vh.tvHeartCount.setText(trade.heartCount);
    }

    @Override
    public int getItemCount() {
        return trades.size();
    }
}

class VH extends RecyclerView.ViewHolder{

    ImageView ivTrade;
    TextView tvTitle, tvPlace, tvCharge, tvBubbleCount, tvHeartCount;

    public VH(@NonNull View itemView) {
        super(itemView);

        ivTrade= itemView.findViewById(R.id.iv_trade);
        tvTitle= itemView.findViewById(R.id.tv_tradetitle);
        tvPlace= itemView.findViewById(R.id.tv_tradeplace);
        tvCharge= itemView.findViewById(R.id.tv_tradecharge);
        tvBubbleCount= itemView.findViewById(R.id.tv_bubblecount);
        tvHeartCount= itemView.findViewById(R.id.tv_heartcount);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Clicked",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
