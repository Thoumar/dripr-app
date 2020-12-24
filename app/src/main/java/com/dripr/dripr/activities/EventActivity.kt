package com.dripr.dripr.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dripr.dripr.R
import com.dripr.dripr.entities.Event

class EventActivity : AppCompatActivity() {

    private var event: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        event = intent.getParcelableExtra("Event")

        handleIntent(intent)
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val appLinkAction = intent.action
        val appLinkData: Uri? = intent.data
        if (Intent.ACTION_VIEW == appLinkAction) {
            appLinkData?.lastPathSegment?.also { eventId ->
                getEventById(eventId)
            }
        }
    }

    private fun getEventById(eventId: String) {
//        val db = Fire
//        val docRef = db.collection("event").document(eventId)
//        docRef.get().addOnCompleteListener { snapshot ->
//            snapshot.to
//            populate
//        }
    }
}