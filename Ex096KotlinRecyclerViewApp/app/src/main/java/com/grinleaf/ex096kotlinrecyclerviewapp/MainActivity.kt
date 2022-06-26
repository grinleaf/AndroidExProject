package com.grinleaf.ex096kotlinrecyclerviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    //멤버변수
    val recycler:RecyclerView by lazy { findViewById(R.id.recycler) } //늦은 초기화 : lateinit 은 val 키워드 사용 불가. 변수가 사용될 때 초기화됨

    //대량의 데이터들 리스트 참조변수 : ArrayList<> --> mutableListOf (변형가능한 리스트)
    var items= mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        items.add(Item("sam","Hello,Kotlin",R.drawable.newyork))
        items.add(Item("robin","Hello,Android",R.drawable.paris))
        items.add(Item("tom","Nice to meet you",R.drawable.sydney))
        items.add(Item("lee","Have a good day",R.drawable.koala))
        items.add(Item("sam","Hello,Kotlin",R.drawable.newyork))
        items.add(Item("robin","Hello,Android",R.drawable.paris))
        items.add(Item("tom","Nice to meet you",R.drawable.sydney))
        items.add(Item("lee","Have a good day",R.drawable.koala))
        
        //코틀린은 setXXX(), getXXX()를 그냥 .XXX = 변수로 사용하는 방식을 권장함
        //멤버변수들은 웬만하면 스스로 get, set 을 가지고 있기 때문에 프로퍼티라고 불림
        recycler.adapter = MyAdapter(this,items)
        
        //리사이클러뷰에 LayoutManager 설정하기
        recycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    override fun onResume() {   //데이터를 서버에서 받아온 후 리사이클러뷰에 보여줄 화면을 갱신하고 싶을 때
        super.onResume()
        recycler.adapter?.notifyDataSetChanged()    //null safety 연산자 주의 : adapter 는 기본적으로 null 로 시작함
    }

    //상단바 옵션메뉴 만들기 : res\menu\option.xml
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //MenuInflater 는 액티비티의 멤버로 존재함 --> get, set 생략
        menuInflater.inflate(R.menu.option,menu)
        return super.onCreateOptionsMenu(menu)
    }

    //옵션메뉴의 아이템 클릭 시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_aa -> Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show()
            R.id.menu_bb -> Toast.makeText(this, "bb", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}