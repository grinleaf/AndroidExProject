package com.grinleaf.ex078viewbinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.grinleaf.ex078viewbinding.databinding.FragmentMyBinding;

public class MyFragment extends Fragment {

    //5. fragment_my.xml 과 연결을 담당하는 클래스가 자동으로 만들어져 있음. 해당 클래스의 이름은 레이아웃파일명을 기반으로 제작되어 있음! (fragment_my - FragmentMyBinding)
    FragmentMyBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentMyBinding.inflate(inflater,container,false);   //inflate 와 함께 findViewById() 까지 됨
        return binding.getRoot();   //최상위 뷰인 RelativeLayout 반환
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btn.setOnClickListener(v->{
            binding.tv.setText("Nice view binding");
        });
    }
}
