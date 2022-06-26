package com.grinleaf.ex027listviewcustom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter{    //BaseAdapter 는 이를 상속하는 클래스들이 형식을 따르도록 하기 위해 이름만있는 메소드(추상메소드) 4개를 만들어둠

    Context context;
    ArrayList<Item> items;

    //생성자 메소드
    public MyAdapter(Context context, ArrayList<Item> items){       //마지막 메소드 사용을 위해 생성자에서 미리 context 를 받아 두기
                                                                    //MainActivity 를 받아도 되지만, 여러 개의 Activity(화면)를 사용하는 프로그램에서 문제 생길 수도..
        this.context= context;                                      //★adapter 를 만들 때 대부분이 파라미터로 (context 기능, 대량의 데이터) 를 받는 형태이다.
        this.items= items;
    }
    
    //이 메소드가 리턴한 숫자(int) 만큼 뷰가 만들어짐.
    @Override
    public int getCount() {
        return items.size();
    }

    //파라미터의 전달된 position = i 의 아이템을 리턴해주는 기능 메소드
    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    //아이템 클릭 시 position 말고 id 로 구분하고자 할 때, position 별로 id를 정하는 기능 메소드 ( 통상적으로는 position 과 id 를 같게 만들기 때문에 그냥 i 값 리턴하면 됨 )
    @Override
    public long getItemId(int i) {
        return i;
    }
    //위의 메소드들은 거의 고정값이라 잘 변하지 않음!
    
    //★★ 항목 1개의 뷰를 만들어서 리턴해주는 기능 메소드. 직접 사용하지 않고, adapter 에게 대리 맡김 ㅇㅅㅇ 중요! ★★
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //첫번째 파라미터 i : position. 현재 만들어야할 번째 의 아이템 위치 position! (0부터 시작~)
        //두번째 파라미터 view : 재활용하는 View 가 있다면 참조. 없으면 null
        //세번째 파라미터 viewGroup : 이 adapter 가 보여줄 listView 참조변수

        //이 메소드는 작업이 크게 두 가지로 나뉨
        //1. create View : 리스트에 보여질 항목(Item) 하나의  View 객체 생성 --> 위의 getCount() 메소드의 반환값 만큼 반복됨!

        //layout 폴더 안의 listview_item.xml 을 읽어와 뷰 객체로 만들어 주는(inflate) [ LayoutInflater 객체 ] --> 뷰를 만들어달라고 요청

        //재활용할 뷰가 없는지 확인
        if(view==null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view= inflater.inflate(R.layout.listview_item, viewGroup, false);
            //첫번째 파라미터 문서로 뷰를 만들되, 두번째 파라미터(=메소드의 세번째 파라미터로 받아온 것)에 붙일 것/붙이지 않을 것이다.(세번째 파라미터 : 붙일지 안붙일지 여부)
        }   //if 문이 true 이면 view 를 viewGroup 에 붙여 만들고, false 이면 그냥 view 를 그대로 만들어 반환

        
        //2. bind View : 만들어진 View 객체 안에 데이터를 연결해주는 작업
            //인스타그램 피드에 올라올 뷰들을 처음부터 한꺼번에 100개 1000개 만들어서 스크롤하는 게 아니라, 위로 올라간 뷰를 다시 가져와 재활용하여 메모리 절약하는 것
        ImageView iv= view.findViewById(R.id.item_iv);  //이 때의 view 는 ImageView, TextView 를 감싸고 있는 틀
        TextView tvName= view.findViewById(R.id.item_tv_name);
        TextView tvNation= view.findViewById(R.id.item_tv_nation);

        //이 메소드의 (첫번째 파라미터 = 위치 position)번째의 데이터 가져오기
        Item item= items.get(i);
        iv.setImageResource(item.imgId);
        tvName.setText(item.name);
        tvNation.setText(item.nation);

        return view;
    } 

    
}
