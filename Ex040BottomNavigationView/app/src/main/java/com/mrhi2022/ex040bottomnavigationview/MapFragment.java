package com.mrhi2022.ex040bottomnavigationview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MapFragment extends Fragment {

    ArrayList<MapFragmentRecyclerItem> items= new ArrayList<>();
    RecyclerView recyclerView;
    MapFragmentRecyclerAdapter adapter;

    ArrayList<MapFragmentRecyclerItem> items2= new ArrayList<>();
    RecyclerView recyclerView2;
    MapFragmentRecyclerAdapter adapter2;

    ArrayList<MapFragmentRecyclerItem> items3= new ArrayList<>();
    RecyclerView recyclerView3;
    MapFragmentRecyclerAdapter adapter3;

    //프레그먼트에서 사용하는 데이터는 프레그먼트가 처음 생성될때 한번만 호출되는
    //이 콜백메소드에 추가함
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        items.add( new MapFragmentRecyclerItem() );
        items.add( new MapFragmentRecyclerItem() );
        items.add( new MapFragmentRecyclerItem() );
        items.add( new MapFragmentRecyclerItem() );
        items.add( new MapFragmentRecyclerItem() );
        items.add( new MapFragmentRecyclerItem() );
        items.add( new MapFragmentRecyclerItem() );

        items2.add( new MapFragmentRecyclerItem() );
        items2.add( new MapFragmentRecyclerItem() );
        items2.add( new MapFragmentRecyclerItem() );
        items2.add( new MapFragmentRecyclerItem() );
        items2.add( new MapFragmentRecyclerItem() );
        items2.add( new MapFragmentRecyclerItem() );
        items2.add( new MapFragmentRecyclerItem() );
        items2.add( new MapFragmentRecyclerItem() );
        items2.add( new MapFragmentRecyclerItem() );
        items2.add( new MapFragmentRecyclerItem() );
        items2.add( new MapFragmentRecyclerItem() );

        items3.add( new MapFragmentRecyclerItem() );
        items3.add( new MapFragmentRecyclerItem() );
        items3.add( new MapFragmentRecyclerItem() );
        items3.add( new MapFragmentRecyclerItem() );
        items3.add( new MapFragmentRecyclerItem() );
        items3.add( new MapFragmentRecyclerItem() );
        items3.add( new MapFragmentRecyclerItem() );
        items3.add( new MapFragmentRecyclerItem() );
        items3.add( new MapFragmentRecyclerItem() );
        items3.add( new MapFragmentRecyclerItem() );

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView= view.findViewById(R.id.recycler);
        adapter= new MapFragmentRecyclerAdapter(getActivity(), items);
        recyclerView.setAdapter(adapter);

        recyclerView2= view.findViewById(R.id.recycler2);
        adapter2= new MapFragmentRecyclerAdapter(getActivity(), items2);
        recyclerView2.setAdapter(adapter2);

        recyclerView3= view.findViewById(R.id.recycler3);
        adapter3= new MapFragmentRecyclerAdapter(getActivity(), items3);
        recyclerView3.setAdapter(adapter3);

    }
}
