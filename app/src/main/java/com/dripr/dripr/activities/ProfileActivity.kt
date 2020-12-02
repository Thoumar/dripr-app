package com.dripr.dripr.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dripr.dripr.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val currentUser = Firebase.auth.currentUser

        val options = RequestOptions
            .circleCropTransform()
            .placeholder(R.drawable.ic_profile)

        Glide
            .with(this)
            .asBitmap()
            .load(currentUser?.photoUrl)
            .apply(options)
            .into(profilePicture)

        onBoardingBtn.setOnClickListener {
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }

        logOutBtn.setOnClickListener {
            Firebase.auth.signOut().also {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }
}