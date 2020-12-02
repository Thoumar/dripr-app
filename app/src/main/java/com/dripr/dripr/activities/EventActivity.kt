package com.dripr.dripr.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dripr.dripr.R
import com.dripr.dripr.entities.Event
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        val event = intent.getParcelableExtra<Event>("EVENT")

        Glide
            .with(this)
            .load(event?.infos?.cover)
            .into(app_bar_cover)

    }
}