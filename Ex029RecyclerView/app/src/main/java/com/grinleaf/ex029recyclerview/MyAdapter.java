package com.grinleaf.ex029recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Item> items= new ArrayList<>();

    public MyAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    //재활용할 뷰가 없어서 뷰를 새로 만들어야할 때 자동으로 실행되는 메소드
    //항목(Item)뷰를 만들고 참조값을 가지고 있는 ViewHolder 를 반환하는 메소드
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView= inflater.inflate(R.layout.recycler_item, parent,false);  //적용시킬 뷰의 형태를 inflate

        VH holder= new VH(itemView);    //inflate 된 뷰 객체를 holder 에 대입

        return holder;  //holder 반환
    }
    
    //해당 위치(position)의 항목뷰(자식뷰)에 items 의 값을 연결(set)해주는 메소드
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //첫번째 파라미터에 들어온 holder 의 자료형이 VH 가 상속받은 부모 클래스의 자료형. 
        VH vh= (VH)holder;

        //해당 번째 아이템 얻어오기
        Item item= items.get(position);
        vh.tvName.setText(item.name);
        vh.tvMsg.setText(item.message);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    
    //이너클래스로 ViewHolder 만들기 : 자식 뷰들의 참조변수를 저장하고 있을 클래스 (생성자가 반드시 필요하다!)
    class VH extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvMsg;
        
        //생성자 메소드 : 자식뷰들을 가진 부모뷰를 통해 자식뷰들의 요소 id를 얻어오기 ( 멤버변수로 존재 )
        public VH(@NonNull View itemView) { //itemView = 안쪽의 뷰들을 가지고 있는 현수막
            super(itemView);

            tvName= itemView.findViewById(R.id.tv_name);
            tvMsg= itemView.findViewById(R.id.tv_msg);
            
            //itemView 가 클릭되었을 때 반응하는 리스너 객체 생성 및 설정
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //클릭한 아이템 뷰가 몇번째 위치인지 알아야 설정이 가능 --> VH 홀더 객체에 현재 위치를 알 수 있는 기능 존재
                    int position= getAdapterPosition();     //원래 VH.this.getAdapterPosition(); 이나, 아우터의 멤버이므로 생략가능
                    Item item= items.get(position);         //마찬가지로 생략가능
                    Toast.makeText(context,item.name,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
