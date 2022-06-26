package com.grinleaf.ex097kotlinbnv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.grinleaf.ex097kotlinbnv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val fragments:MutableList<Fragment> = mutableListOf() //굳이 by lazy 할 필요x (미리 만들어져있어도 상관 없음)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fragments.add(Tab1Fragment())
        fragments.add(Tab2Fragment())
        fragments.add(Tab3Fragment())

        supportFragmentManager.beginTransaction().add(R.id.container, fragments[0]).commit() //container= frame 레이아웃

        binding.bnv.setOnItemSelectedListener {
            
//            supportFragmentManager.beginTransaction().hide(fragments[0]).commit() //위에서 이미 추가한 상태
//            supportFragmentManager.beginTransaction().hide(fragments[1]).commit() //아직 프래그먼트가 생성되지 않았을 경우 error 가능성이 있음
//            supportFragmentManager.beginTransaction().hide(fragments[2]).commit() //이하 동문
            //대신 요렇게 쓰기!
            supportFragmentManager.fragments.forEach{
                supportFragmentManager.beginTransaction().hide(it).commit() //it 의 역할
            }

            val tran= supportFragmentManager.beginTransaction()
            when(it.itemId){
                R.id.bnv_menu_tab1->{
                    tran.show(fragments[0])
                }
                R.id.bnv_menu_tab2->{
                    if(!supportFragmentManager.fragments.contains(fragments[1])){    //fragments 배열에 fragments[1] 요소가 추가되어있지않은 상태인지 묻는 구문
                        tran.add(R.id.container, fragments[1])
                    }
                    tran.show(fragments[1])
                }
                R.id.bnv_menu_tab3->{
                    if(!supportFragmentManager.fragments.contains(fragments[2])) tran.add(R.id.container,fragments[2])
                    tran.show(fragments[2])
                }
            }
            tran.commit()
            
            //SAM 변환에서는 return 키워드 생략해야함
            true
        }

    }
}