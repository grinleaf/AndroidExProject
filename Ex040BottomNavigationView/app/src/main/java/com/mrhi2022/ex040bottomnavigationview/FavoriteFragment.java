package com.mrhi2022.ex040bottomnavigationview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FavoriteFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 pager;
    FavoritePagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout= view.findViewById(R.id.layout_tab);

        pager= view.findViewById(R.id.pager);
        adapter= new FavoritePagerAdapter(getActivity());
        pager.setAdapter(adapter);

        //탭레이아웃과 뷰페이저를 연동하는 중재자 객체 생성하여 붙이기!
        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position==0) tab.setText("PAGE1");
                else if(position==1) tab.setText("PAGE2");
            }
        }).attach();

    }
}
