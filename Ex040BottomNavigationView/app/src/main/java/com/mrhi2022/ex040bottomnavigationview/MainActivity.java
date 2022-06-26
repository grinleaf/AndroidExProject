package com.mrhi2022.ex040bottomnavigationview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv;

    FragmentManager fragmentManager;
    ArrayList<Fragment> fragments= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //프레그먼트 관리자부터 소환
        fragmentManager= getSupportFragmentManager();

        //처음에 보여질 Fragment를 1개 만들어 붙이기 [ HomeFragment ]
        fragments.add( new HomeFragment()  );
        fragments.add( null );
        fragments.add( null );

        fragmentManager.beginTransaction().add(R.id.container, fragments.get(0)).commit();

        bnv= findViewById(R.id.bnv);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //Content영역에 Fragment 를 동적으로 추가하여 보여주기
                //프레그먼트를 동적으로 제어하려면 프레그먼트 관리자가 필요함
                FragmentTransaction tran= fragmentManager.beginTransaction();

                //기존에 보여주고 있는 프레그먼트들을 모두 안 보이도록
                if(fragments.get(0)!=null) tran.hide(fragments.get(0));
                if(fragments.get(1)!=null) tran.hide(fragments.get(1));
                if(fragments.get(2)!=null) tran.hide(fragments.get(2));

                switch ( item.getItemId() ){
                    case R.id.menu_bnv_home:
                        tran.show(fragments.get(0));
                        break;

                    case R.id.menu_bnv_fav:
                        if(fragments.get(1)==null){
                            fragments.set(1, new FavoriteFragment());
                            tran.add( R.id.container, fragments.get(1) );
                        }
                        tran.show(fragments.get(1));
                        break;

                    case R.id.menu_bnv_map:
                        if(fragments.get(2)==null){
                            fragments.set(2, new MapFragment());
                            tran.add(R.id.container, fragments.get(2) );
                        }
                        tran.show(fragments.get(2));
                        break;
                }

                //트랜잭션의 작업 완료(적용)
                tran.commit();

                //return 이 true가 아니면 아이템클릭에 따른 효과 색상이 반영되지 않음.
                return true;
            }
        });
    }
}