package com.grinleaf.ex096kotlinrecyclerviewapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val context: Context, val items:MutableList<Item>):RecyclerView.Adapter<MyAdapter.VH>() {

    inner class VH(itemView:View):RecyclerView.ViewHolder(itemView){
        val tvTitle:TextView by lazy{ itemView.findViewById(R.id.tv_title) }
        val tvMsg:TextView by lazy { itemView.findViewById(R.id.tv_msg) }
        val iv: ImageView by lazy { itemView.findViewById(R.id.iv) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater:LayoutInflater= LayoutInflater.from(context)
        val itemView= inflater.inflate(R.layout.recycler_item,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item= items.get(position)
        holder.tvTitle.setText(item.title)  //자바식
        holder.tvMsg.text= item.msg //코틀린식 : set, get 을 선호하지 않음
        holder.iv.setImageResource(item.img)    //Glide 써도 된다~

        //리사이클러뷰의 아이템이 선택될 때의 동작은 여기서 설정!
        //VH 는 기본적으로 본인의 itemView 를 멤버변수로 가지는데, 코틀린에서 get 을 지양하는 표기법 때문에 holder 가 구성요소로 itemView 를 가지고 있는 것처럼 표기됨!
        holder.itemView.setOnClickListener{
            //ItemActivity 를 실행하면서 클릭한 Item 의 정보를 넘겨주기
            val intent= Intent(context,ItemActivity::class.java)
            intent.putExtra("title",item.title)
            intent.putExtra("msg",item.msg)
            intent.putExtra("img",item.img)

            //화면 전환 시 연관된 뷰들에 전환효과를 적용하기 (리사이클러뷰 아이템 클릭 시 클릭효과)
            val optionsCompat:ActivityOptionsCompat= ActivityOptionsCompat.makeSceneTransitionAnimation(context as MainActivity, Pair(holder.iv,"imgAnimation"))//Pair 파라미터(애니메이션 적용할 뷰, 설정한 효과의 식별자)
            context.startActivity(intent,optionsCompat.toBundle())
        }
    }

    //함수의 리턴값을 할당 연산자로 줄여쓰기 : 쓸지 말지는 선택~
//    override fun getItemCount(): Int {
//        return items.size
//    }
    override fun getItemCount(): Int = items.size
}