package com.dripr.dripr.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dripr.dripr.R
import com.dripr.dripr.adapters.ViewPagerMainAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var mainViewPagerMainAdapter: ViewPagerMainAdapter? = null

    public override fun onStart() {
        super.onStart()

        val currentUser = Firebase.auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(R.layout.activity_main)

        initToolbar()
        initViewPager()
    }

    private fun initToolbar() {
        Glide.with(this)
            .load(Firebase.auth.currentUser?.photoUrl.toString())
            .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.ic_profile))
            .into(profilePicture)
    }

    private fun initViewPager() {
        mainViewPagerMainAdapter = ViewPagerMainAdapter(supportFragmentManager)
        mainViewPager.adapter = mainViewPagerMainAdapter
        bottomNavigation.setOnNavigationItemSelectedListener(this)
        mainViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottomNavigation.menu.findItem(R.id.navigation_discover).isChecked = true
                    1 -> bottomNavigation.menu.findItem(R.id.navigation_map).isChecked = true
                    2 -> bottomNavigation.menu.findItem(R.id.navigation_events).isChecked = true
//                    3 -> bottomNavigation.menu.findItem(R.id.navigation_messages).isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_discover -> mainViewPager.currentItem = 0
            R.id.navigation_map -> mainViewPager.currentItem = 1
            R.id.navigation_events -> mainViewPager.currentItem = 2
//            R.id.navigation_messages -> mainViewPager.currentItem = 3
        }
        return true
    }
}