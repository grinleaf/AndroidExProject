package com.grinleaf.ex032fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {

    TextView tv;
    Button btn,btn2;

    //Activity 처럼 해당 Fragment 가 화면에 보여줄 뷰를 만들어 (View 를)리턴해주는 작업을 해주는 콜백 메소드 ( MainActivity 의 onCreate() 기능 ) [ onCreateView() ]
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //이미 inflater 를 사용할 걸 알고 파라미터로 넣어둠 ^___^ 세상 좋다
        View view= inflater.inflate(R.layout.fragment_my,container,false);  //두번째 파라미터 : 붙을 위치(ViewGroup 은 프래그먼트의 위치)
        return view;
    }

    //뷰가 만들어진 후( onCreateView() 실행 이후 ), 뷰들의 참조변수에 대입해주는 작업을 위한 콜백 메소드 [ onViewCreated() ]

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {    //첫번째 파라미터 view : onCreate()의 반환값 view
        tv= view.findViewById(R.id.tv);
        btn= view.findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("Nice to meet you");
            }
        });

        btn2= view.findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //해당 Fragment 를 보여주는 액티비티의 TextView 글씨를 변경해보기
                //MainActivity 의 TextView 에 접근하기 --> 모든 Fragment 에는 본인을 보여주는 액티비티 객체를 소환하는 기능 메소드가 있다! [ getActivity() ]
                MainActivity ac= (MainActivity) getActivity();
                ac.tv.setText("Hello World!!");
            }
        });

    }
}
