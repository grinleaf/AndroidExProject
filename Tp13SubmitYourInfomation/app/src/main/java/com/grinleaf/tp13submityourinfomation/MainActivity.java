package com.grinleaf.tp13submityourinfomation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyAdapter adapter;
    ArrayList<Card> cards= new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_btn).setOnClickListener(view -> {
            Intent intent= new Intent(this, SecondActivity.class);
            startActivityForResult(intent,10);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    String name= data.getStringExtra("name");
                    String nick= data.getStringExtra("nick");
                    String title= data.getStringExtra("title");
                    String text= data.getStringExtra("text");

                    cards.add(new Card(name,nick,title,text));

                    recyclerView= findViewById(R.id.recycler);
                    adapter= new MyAdapter(this, cards);
                    recyclerView.setAdapter(adapter);
                }
        }
    }
}