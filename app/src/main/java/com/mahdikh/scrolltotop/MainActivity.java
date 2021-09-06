package com.mahdikh.scrolltotop;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mahdikh.scrolltotop.ui.RecyclerViewAdapter;
import com.mahdikh.vision.scrolltotop.animator.FadeAnimator;
import com.mahdikh.vision.scrolltotop.animator.FlyAnimator;
import com.mahdikh.vision.scrolltotop.animator.ScaleAnimator;
import com.mahdikh.vision.scrolltotop.animator.SlideAnimator;
import com.mahdikh.vision.scrolltotop.widget.ScrollToTop;

public class MainActivity extends AppCompatActivity {
    private ScrollToTop scrollToTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        scrollToTop = findViewById(R.id.scrollToTop);

        recyclerView.setAdapter(new RecyclerViewAdapter());
        scrollToTop.setupWithRecyclerView(recyclerView);
        scrollToTop.setSmoothScroll(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();
        if (item.isCheckable()) {
            item.setChecked(!item.isChecked());
        }
        if (id == R.id.item_fade) {
            scrollToTop.setAnimator(new FadeAnimator());
        } else if (id == R.id.item_fly) {
            scrollToTop.setAnimator(new FlyAnimator());
        } else if (id == R.id.item_scale) {
            scrollToTop.setAnimator(new ScaleAnimator());
        } else if (id == R.id.item_slide) {
            scrollToTop.setAnimator(new SlideAnimator());
        } else if (id == R.id.item_noAnimator) {
            scrollToTop.setAnimator(null);
        } else if (id == R.id.item_smoothScroll) {
            scrollToTop.setSmoothScroll(item.isChecked());
        } else if (id == R.id.item_heavyCheckup) {
            scrollToTop.setHeavyCheckup(item.isChecked());
        } else if (id == R.id.item_shortScroll) {
            scrollToTop.setShortScroll(item.isChecked());
        }
        return true;
    }
}