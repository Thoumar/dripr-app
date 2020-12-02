package com.dripr.dripr.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dripr.dripr.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenActivity : AppCompatActivity() {

    private val isLoggedIn: Boolean = false
    private var isFirstRun: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true)

        GlobalScope.launch {
            delay(3000L)
            checkLogin()
        }
    }

    private fun checkLogin() {
        if (isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            if (isFirstRun) {
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).apply()
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }
}