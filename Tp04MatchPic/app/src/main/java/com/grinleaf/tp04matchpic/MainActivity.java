package com.grinleaf.tp04matchpic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton startBtn, howToPlayBtn;
    ImageView board;
    ImageView ani1,ani2,ani3,ani4,ani5;
    Integer[] ivImg= {R.drawable.a_ele,R.drawable.a_frog,R.drawable.a_lion,R.drawable.a_monkey,R.drawable.a_pig};
    Integer[] ivBoard= {R.drawable.b_ele,R.drawable.b_frog,R.drawable.b_lion,R.drawable.b_monkey,R.drawable.b_pig};
    List<Integer> arrayImg= Arrays.asList(ivImg);
    List<Integer> arrayBoard= Arrays.asList(ivBoard);
    int boardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn= findViewById(R.id.btn01);
        howToPlayBtn= findViewById(R.id.btn02);

        board= findViewById(R.id.board_imgv);

        ani1= findViewById(R.id.animal01);
        ani2= findViewById(R.id.animal02);
        ani3= findViewById(R.id.animal03);
        ani4= findViewById(R.id.animal04);
        ani5= findViewById(R.id.animal05);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameStart();

            }

        });

        howToPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("게임설명");
                builder.setMessage("표지판의 단어와 그림이 일치하면 WIN!");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

    }

    void clickImage(int i){
        if((arrayImg.get(i)+86)==boardView){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("You WIN!");
            builder.setMessage("다시 한 번 더?");
            builder.setPositiveButton("한 번 더", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    gameStart();
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }else{
            Toast.makeText(this, "땡!", Toast.LENGTH_SHORT).show();
        }
    }
    void play(){
        ani1.setOnClickListener(v->clickImage(0));
        ani2.setOnClickListener(v->clickImage(1));
        ani3.setOnClickListener(v->clickImage(2));
        ani4.setOnClickListener(v->clickImage(3));
        ani5.setOnClickListener(v->clickImage(4));
    }

    void gameStart(){
        board.setImageResource(R.drawable.q_board);

        Collections.shuffle(arrayImg);
        arrayImg.toArray(ivImg);
        Collections.shuffle(arrayBoard);
        arrayBoard.toArray(ivBoard);
        boardView = arrayBoard.get(0);

        ani1.setImageResource(arrayImg.get(0));
        ani2.setImageResource(arrayImg.get(1));
        ani3.setImageResource(arrayImg.get(2));
        ani4.setImageResource(arrayImg.get(3));
        ani5.setImageResource(arrayImg.get(4));

        board.setImageResource(arrayBoard.get(0));
        for(int i=0; i<arrayImg.size(); i++){
            switch (boardView){
                case R.drawable.b_ele:  //Elephant 보드 일 때, R.drawable.a_ele를 가진 aniamlView 가 클릭되면
                    play();
                    break;
                case R.drawable.b_frog:
                    play();
                    break;
                case R.drawable.b_lion:
                    play();
                    break;
                case R.drawable.b_monkey:
                    play();
                    break;
                case R.drawable.b_pig:
                    play();
                    break;
            }
        };
    }
}