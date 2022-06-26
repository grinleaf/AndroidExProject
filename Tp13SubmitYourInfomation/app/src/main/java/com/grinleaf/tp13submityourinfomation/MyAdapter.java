package com.grinleaf.tp13submityourinfomation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Card> cards= new ArrayList<>();

    public MyAdapter(Context context, ArrayList<Card> cards) {
        this.context = context;
        this.cards = cards;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_card, parent,false);
        VH holder= new VH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        VH vh= (VH) holder;

        Card card= cards.get(position);
        vh.name.setText(card.name);
        vh.nick.setText(card.nick);
        vh.title.setText(card.title);
        vh.text.setText(card.text);

        //holder 에 있는 대량 데이터를 setText 하면되는디...


    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    class VH extends RecyclerView.ViewHolder{
        TextView name;
        TextView nick;
        TextView title;
        TextView text;

        public VH(@NonNull View itemView) {
            super(itemView);

            name= itemView.findViewById(R.id.card_name);
            nick= itemView.findViewById(R.id.card_nick);
            title= itemView.findViewById(R.id.card_title);
            text= itemView.findViewById(R.id.card_text);
        }
    }
}
