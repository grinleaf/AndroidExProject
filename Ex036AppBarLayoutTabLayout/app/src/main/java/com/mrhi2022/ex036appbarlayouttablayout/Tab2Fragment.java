package com.mrhi2022.ex036appbarlayouttablayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Tab2Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab2, container, false);
    }

    //이 프레그먼트가 옵션메뉴를 가지고 있다고 명시해야만 옵션메뉴가 표시됨
    //프레그먼트객체가 생성될때 자동으로 발동하는 메소드 [ onCreateView()보다 먼저 실행됨 ]
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //이 프레그먼트가 옵션메뉴를 가지고 있다고 명시
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.tab2, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity ac= (MainActivity) getActivity();
        ac.getSupportActionBar().setSubtitle("SHOP");
    }
}
