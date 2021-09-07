package com.mahdikh.scrolltotop

import androidx.appcompat.app.AppCompatActivity
import com.mahdikh.vision.scrolltotop.widget.ScrollToTop
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.mahdikh.vision.scrolltotop.animator.FadeAnimator
import com.mahdikh.vision.scrolltotop.animator.FlyAnimator
import com.mahdikh.vision.scrolltotop.animator.ScaleAnimator
import com.mahdikh.vision.scrolltotop.animator.SlideAnimator

class MainActivity : AppCompatActivity() {
    private lateinit var scrollToTop: ScrollToTop

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = RecyclerViewAdapter()

        scrollToTop = findViewById(R.id.scrollToTop)
        scrollToTop.setupWithRecyclerView(recyclerView)
        scrollToTop.isSmoothScroll = true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (item.isCheckable) {
            item.isChecked = !item.isChecked
        }
        when (id) {
            R.id.item_fade -> {
                scrollToTop.animator = FadeAnimator()
            }
            R.id.item_fly -> {
                scrollToTop.animator = FlyAnimator()
            }
            R.id.item_scale -> {
                scrollToTop.animator = ScaleAnimator()
            }
            R.id.item_slide -> {
                scrollToTop.animator = SlideAnimator()
            }
            R.id.item_noAnimator -> {
                scrollToTop.animator = null
            }
            R.id.item_smoothScroll -> {
                scrollToTop.isSmoothScroll = item.isChecked
            }
            R.id.item_heavyCheckup -> {
                scrollToTop.isHeavyCheckup = item.isChecked
            }
            R.id.item_shortScroll -> {
                scrollToTop.isShortScroll = item.isChecked
            }
        }
        return true
    }
}