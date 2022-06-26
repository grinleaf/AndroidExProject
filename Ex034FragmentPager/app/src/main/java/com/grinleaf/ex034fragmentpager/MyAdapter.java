package com.grinleaf.ex034fragmentpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyAdapter extends FragmentStateAdapter {

    //주의! '프래그먼트' 3개를 가진 배열이 아니라, 프래그먼트를 참조하는 '참조변수' 3개를 가진 배열 객체임~!
    Fragment[] fragments= new Fragment[3];

    //생성자 메소드 : (상속관계)Context <- Activity <- FragmentActivity <- AppComfatActivity <- MainActivity
    public MyAdapter(@NonNull FragmentActivity fragmentActivity, Fragment[] fragments) {    //
        super(fragmentActivity);

        //페이지별로 보여줄 Fragment 객체 생성 및 배열요소로 추가
        fragments[0]= new Page1Fragment();
        fragments[1]= new Page2Fragment();
        fragments[2]= new Page3Fragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position]; //위의 생성자 메소드에서 각 position 별로 배열요소로 Fragment 페이지를 참조해둔 상태이므로, 해당 프래그먼트를 반환함
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
