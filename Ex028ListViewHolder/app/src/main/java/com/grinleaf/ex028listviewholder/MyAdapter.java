package com.grinleaf.ex028listviewholder;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> items;

    //생성자 메소드
    public MyAdapter(Context context, ArrayList<String> items){
        this.context= context;
        this.items= items;
    }

    //어댑터가 만들 뷰의 개수
    @Override
    public int getCount() {
        return items.size();
    }

    //i 번째의 요소 가져오기
    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    //i 번째의 요소의 위치 가져오기
    @Override
    public long getItemId(int i) {
        return i;
    }

    //getView 는 items.size()만큼 반복됨. 리스트뷰가 보여줄 item 한 개의 뷰 객체를 만들어 리턴
    //첫번째 파라미터 i : 현재 만들어야할 번째 위치번호(position)
    //두번째 파라미터 view : 재활용할 뷰가 있다면 참조, 없다면 null 값
    //세번째 파라미터 viewGroup : ListView
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //1. create view : 아이템 한 개를 만들기 (현수막 하나)
        if(view==null){ //재활용할 뷰가 있어서 null이 아니면, 해당 뷰에 내용물(값)만 바꿔주면 되는데, null 이면 값을 바꿀 일이 없으니 뷰 새로만들기
            //layout 폴더 안에 있는 listview_item.xml 문서를 읽어와 뷰 객체로 만들어주는 능력을 가진 객체 [ LayoutInflater ]
            LayoutInflater inflater= LayoutInflater.from(context);      //원래 코드는 매우 길다... context.getSystemService()...머시기저시기
            view= inflater.inflate(R.layout.listview_item,viewGroup);
            ViewHolder holder= new ViewHolder(view);    //해당 뷰의 자식 뷰들의 참조변수를 멤버로 가짐
                                                        //(매번 스택영역에 지역변수 만들었다 지웠다하면서 동작 늘리지 않고, 프로그램 끝날 때까지 메모리할당이 유지되는 멤버변수로서 존재하도록 함)
            view.setTag(holder);    //모든 뷰가 가지고 있는 변수 tag 안에 holder 객체를 넣음 (변수 tag 는 파라미터 view 의 소속 = 아이템 한 개의 틀. 현수막)
        }

        //2. bind view
        //만들어진 view 안의 TextView 를 찾아오는 findViewById() 메소드가 처리속도를 저하시키는 문제점 발생
        //TextView tv= view.findViewById(R.id.item_tv);   //현수막에서 textview 를 찾아 id를 가져옴

        //뷰 안의 tag 변수에 저장된 ViewHolder 객체 꺼내오기
        ViewHolder holder= (ViewHolder) view.getTag();

        //현재 만들어야할 번째 데이터
        String item= items.get(i);
        holder.tv.setText(item);

        return view;
    }
    
    //아이템 1개 뷰의 자식 뷰들의 참조변수를 멤버로 가지는 클래스
    class ViewHolder{
        TextView tv;
        
        public ViewHolder(View itemView){
            tv= itemView.findViewById(R.id.item_tv);
        }
    }
    
    
}
