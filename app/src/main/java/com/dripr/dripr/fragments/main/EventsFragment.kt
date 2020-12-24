package com.dripr.dripr.fragments.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dripr.dripr.R
import com.dripr.dripr.activities.CreateEventActivity
import com.dripr.dripr.activities.EventActivity
import com.dripr.dripr.adapters.events.viewholders.EventsAdapter
import com.dripr.dripr.entities.Event
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_events.view.*

class EventsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_events, container, false)

        FirebaseFirestore.getInstance().collection("events")
            .whereEqualTo("ownerId", Firebase.auth.uid)
            .get()
            .addOnSuccessListener { documents ->
                val events = ArrayList<Event>()
                for (document in documents) {
                    val event = document.toObject(Event::class.java).also { it.id = document.id }
                    events.add(event)
                }

                v.yourEventsRecyclerView.apply {
                    layoutManager =
                        LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
                    adapter = EventsAdapter("horizontal", events) { event -> onEventClick(event) }
                }
            }

        v.createEventBtn.setOnClickListener {
            val i = Intent(this.activity, CreateEventActivity::class.java)
            startActivity(i)
        }

        return v
    }

    private fun onEventClick(event: Event) {
        val i = Intent(this.activity, EventActivity::class.java)
        startActivity(i)
    }
}