package com.grinleaf.tp06adapterviewex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context context;
    ArrayList<Item> items;

    //생성자메소드
    public MyAdapter(Context context, ArrayList<Item> items){
        this.context= context;
        this.items= items;
    }

    //리턴값만큼 뷰 생성
    @Override
    public int getCount() {
        return items.size();
    }

    //파라미터 i의 위치에 있는 Item 객체 리턴
    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    //items 의 각 요소(Item 객체)가 클릭되었을 때 사용할 id 값의 설정을 위해, 해당 객체의 position 마다 id 값을 부여해주는 메소드
    @Override
    public long getItemId(int i) {
        return i;
    }

    //위의 메소드들은 거의 대부분의 상황에 고정값

    //
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null){
            LayoutInflater inflater= LayoutInflater.from(context);  //이미 Context 객체가 생성되어 있기 때문에, 객체 생성필요 x
            view= inflater.inflate(R.layout.listview_item, viewGroup, false);
        }

        ImageView iv= view.findViewById(R.id.item_iv);
        TextView name= view.findViewById(R.id.item_tv_name);
        TextView nation= view.findViewById(R.id.item_tv_nation);

        Item item= items.get(i);
        iv.setImageResource(item.imgIv);
        name.setText(item.name);
        nation.setText(item.nation);

        return view;
    }
}
