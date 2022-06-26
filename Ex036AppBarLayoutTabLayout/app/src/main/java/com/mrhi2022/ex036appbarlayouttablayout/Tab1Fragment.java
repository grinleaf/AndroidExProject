package com.mrhi2022.ex036appbarlayouttablayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Tab1Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }

    //탭1이 화면에 보여질때 자동으로 발동하는 콜백메소드(라이프사이클메소드)
    @Override
    public void onResume() {
        super.onResume();
        //액티비티의 sub title 을 변경
        MainActivity ac= (MainActivity)getActivity();
        ac.getSupportActionBar().setSubtitle("HOME");
    }
}
