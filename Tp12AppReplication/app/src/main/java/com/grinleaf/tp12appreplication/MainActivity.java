package com.grinleaf.tp12appreplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<ToolbarGrid> gridItems= new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView= findViewById(R.id.layout_toolbar_grid);

        for(int i=0; i<12; i++) {
            gridItems.add(new ToolbarGrid(R.drawable.ic_baseline_airline_seat_recline_extra_24+i,R.array.toolbar_grid+i));
        }
    }
}