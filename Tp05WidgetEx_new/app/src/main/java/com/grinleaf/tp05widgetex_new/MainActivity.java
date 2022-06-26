package com.grinleaf.tp05widgetex_new;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView more, iv, di, db, heart, talkbubble, send, save;

    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        more= findViewById(R.id.more);
        iv= findViewById(R.id.iv);
        heart= findViewById(R.id.heart);
        talkbubble= findViewById(R.id.talkbubble);
        send= findViewById(R.id.send);
        save= findViewById(R.id.save);
        di= findViewById(R.id.dialog_image);
        db= findViewById(R.id.dialog_button);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"more information",Toast.LENGTH_SHORT).show();
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setView(R.layout.moanadialog);

                AlertDialog dialog= builder.create();
                dialog.show();

                db.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(i<5) {
                            di.setImageResource(R.drawable.moana01 + i);
                            i++;
                        } else i=0;
                    }
                });
            }
        });



        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"add heart",Toast.LENGTH_SHORT).show();
            }
        });

        talkbubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"message",Toast.LENGTH_SHORT).show();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"send another",Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"save this image",Toast.LENGTH_SHORT).show();
            }
        });






    }
}