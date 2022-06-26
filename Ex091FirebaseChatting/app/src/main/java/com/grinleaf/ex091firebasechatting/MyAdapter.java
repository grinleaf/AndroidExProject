package com.grinleaf.ex091firebasechatting;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

    Context context;
    ArrayList<MessageItem> messageItems;

    public MyAdapter(Context context, ArrayList<MessageItem> messageItems) {
        this.context = context;
        this.messageItems = messageItems;
    }

    //변하지않는 상수로 만들어 가독성 좋게 만들기!
    final int TYPE_MY= 0;
    final int TYPE_OTHER= 1;

    //리사이클러뷰가 보여줄 뷰의 종류(모양)이 다른 경우, 해당 아이템 마다 뷰타입(viewType)을 정하여 리턴해주는 메소드 : [ getItemViewType() ]
    //리턴되는 int 값이 onCreateViewHolder() 의 파라미터 viewType 에 들어감
    @Override
    public int getItemViewType(int position) {
        if(messageItems.get(position).name.equals(G.nickname))
             return TYPE_MY;    //호출된 position 의 아이템 객체의 name 이 글로벌 변수인 닉네임과 같다면 0, 아니면 1
        else return TYPE_OTHER; //0이면 my_messagebox / 1이면 other_messagebox inflate 하기 위함
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //두번째 파라미터 viewType : inflate 할 뷰의 타입이 여러개일 때 이용
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView= null;
        if(viewType==TYPE_MY) itemView= inflater.inflate(R.layout.my_messagebox,parent,false);
        else itemView= inflater.inflate(R.layout.other_messagebox,parent,false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        MessageItem item= messageItems.get(position);

        holder.tvName.setText(item.name);
        holder.tvMsg.setText(item.message);
        holder.tvTime.setText(item.time);
        Glide.with(context).load(item.profileUrl).into(holder.civ);
    }

    @Override
    public int getItemCount() {
        return messageItems.size();
    }

    class VH extends RecyclerView.ViewHolder{

        //11. 바인딩클래스를 사용하기에 효율적이지 않음 (other_messagebox.xml 과 my_messagebox.xml 두 종류의 레이아웃을 바인딩해야함) --> 기존 findViewById 방식으로.
        //바인딩클래스를 사용하지 않으므로, 바인딩클래스가 자동으로 만들어지지 않도록 (메모리 낭비 방지) xml 문서에 설정 가능! --> viewBindingIgnore 속성
        CircleImageView civ;
        TextView tvName;
        TextView tvMsg;
        TextView tvTime;

        public VH(@NonNull View itemView) {
            super(itemView);

            civ= itemView.findViewById(R.id.civ);
            tvName= itemView.findViewById(R.id.tv_name);
            tvMsg= itemView.findViewById(R.id.tv_msg);
            tvTime= itemView.findViewById(R.id.tv_time);
        }
    }
}
