package com.mrhi2022.ex033fragment2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {
    //이 프레그먼트가 보여줄 뷰를 만들어 리턴해주는 콜백 메소드
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //전달받은 Argument를 받아오기
        Bundle bundle= getArguments();
        String name= bundle.getString("name","");
        int age= bundle.getInt("age", -1);

        Toast.makeText(getActivity(), name+", "+ age, Toast.LENGTH_SHORT).show();

    }
}
