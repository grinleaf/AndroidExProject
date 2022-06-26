package com.grinleaf.ex034fragmentpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ViewPager2 pager;
    MyAdapter adapter;

    Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager= findViewById(R.id.pager);
        adapter= new MyAdapter(this,fragments);
        pager.setAdapter(adapter);


    }
}