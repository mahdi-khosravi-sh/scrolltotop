package com.mahdikh.scrolltotop;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mahdikh.scrolltotop.ui.RecyclerViewAdapter;
import com.mahdikh.vision.scrolltotop.widget.ScrollToTop;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ScrollToTop scrollToTop = findViewById(R.id.scrollToTop);

        recyclerView.setAdapter(new RecyclerViewAdapter());
        scrollToTop.setupWithRecyclerView(recyclerView);
    }
}