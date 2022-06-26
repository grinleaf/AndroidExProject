package com.grinleaf.ex033fragment2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {

    //해당 프래그먼트가 보여줄 뷰를 만들어 리턴해주는 콜백 메소드 [ onCreateView() ]
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //전달받은 Argument 를 받아오기 : MainActivity 에서 Bundle 을 받아왔으니 자료형 맞춰주자~
        Bundle bundle= getArguments();
        String name= bundle.getString("name");  //파라미터 (식별자, defaultValue:번들에 값이 없을 경우 나타날 기본값)
        int age= bundle.getInt("age",-1);

        Toast.makeText(getActivity(),name+", "+age, Toast.LENGTH_SHORT).show();

    }
}
