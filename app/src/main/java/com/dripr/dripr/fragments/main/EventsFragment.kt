package com.dripr.dripr.fragments.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dripr.dripr.R
import com.dripr.dripr.activities.CreateEventActivity
import kotlinx.android.synthetic.main.fragment_events.view.*

class EventsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_events, container, false)

        v.createEventBtn.setOnClickListener {
            val i = Intent(this.activity, CreateEventActivity::class.java)
            startActivity(i)
        }

        return v
    }
}