package com.dripr.dripr.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.dripr.dripr.R
import com.dripr.dripr.adapters.ViewPagerOnBoardingAdapter
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {

    private var onBoardingViewPagerAdapter: ViewPagerOnBoardingAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        initViewPager()
        initDotsIndicator()

        pass.setOnClickListener {
            onBoardingViewPager.setCurrentItem(3, true)
        }

        start.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }

    private fun initViewPager() {
        onBoardingViewPagerAdapter = ViewPagerOnBoardingAdapter(supportFragmentManager)
        onBoardingViewPager.adapter = onBoardingViewPagerAdapter
        onBoardingViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> setNextDisplay()
                    1 -> setNextDisplay()
                    2 -> setNextDisplay()
                    3 -> setStartDisplay()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setStartDisplay() {
        if (pass.visibility == View.VISIBLE) {
            pass.visibility = View.GONE
            start.visibility = View.VISIBLE
        }
    }

    private fun setNextDisplay() {
        if (start.visibility == View.VISIBLE) {
            start.visibility = View.GONE
            pass.visibility = View.VISIBLE
        }
    }

    private fun initDotsIndicator() {
        dotsIndicator.setViewPager(onBoardingViewPager)
    }
}