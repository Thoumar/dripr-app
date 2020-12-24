package com.dripr.dripr.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dripr.dripr.R
import com.dripr.dripr.adapters.events.viewholders.EventsAdapter
import com.dripr.dripr.adapters.users.UsersAdapter
import com.dripr.dripr.entities.Event
import com.dripr.dripr.entities.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    var searchEntity: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val yellowColorValue = ContextCompat.getColor(this, R.color.accentYellow)
        val whiteColorValue = ContextCompat.getColor(this, R.color.white)

        val db = FirebaseFirestore.getInstance()

        validateSearchButton.setOnClickListener {
            val searchStr = searchInputEditText.text.toString()

            if (!searchStr.isNullOrEmpty()) {

                if (searchEntity === "event") {
                    Snackbar.make(searchContainer, "Searching for events", Snackbar.LENGTH_LONG)
                        .show()
                    // User is searching for events
                    db.collection("events").whereEqualTo("name", searchStr).get()
                        .addOnSuccessListener { documents ->
                            val events = ArrayList<Event>()
                            for (document in documents) {
                                val event = document.toObject(Event::class.java)
                                    .also { it.id = document.id }
                                events.add(event)
                            }

                            resultsRecyclerView.visibility = View.VISIBLE
                            resultsRecyclerView.apply {
                                layoutManager = LinearLayoutManager(
                                    this.context,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                adapter = EventsAdapter(
                                    "vertical",
                                    events
                                ) { event -> onEventClick(event) }
                            }
                        }

                } else if (searchEntity === "people") {
                    Snackbar.make(searchContainer, "Searching for peoples", Snackbar.LENGTH_LONG)
                        .show()
                    // User is searching for people
                    db.collection("users").whereEqualTo("pseudo", searchStr).get()
                        .addOnSuccessListener { documents ->
                            val users = ArrayList<User>()
                            for (document in documents) {
                                val user =
                                    document.toObject(User::class.java).also { it.id = document.id }
                                users.add(user)
                            }
                            resultsRecyclerView.visibility = View.VISIBLE
                            resultsRecyclerView.apply {
                                layoutManager = LinearLayoutManager(
                                    this.context,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                adapter =
                                    UsersAdapter("vertical", users) { user -> onUserClick(user) }
                            }
                        }

                } else {
                    // Something went wrong
                    Snackbar.make(searchContainer, "Something went wrong", Snackbar.LENGTH_LONG)
                        .show()

                }
            } else {
                // Search input is empty
                Snackbar.make(searchContainer, "Search input is empty", Snackbar.LENGTH_LONG).show()
            }


        }

        selectEvent.setOnClickListener {
            selectEvent.backgroundTintList = ColorStateList.valueOf(yellowColorValue)
            selectPeople.backgroundTintList = ColorStateList.valueOf(whiteColorValue)
            searchEntity = "event"
        }

        selectPeople.setOnClickListener {
            selectPeople.backgroundTintList = ColorStateList.valueOf(yellowColorValue)
            selectEvent.backgroundTintList = ColorStateList.valueOf(whiteColorValue)
            searchEntity = "people"
        }
    }


    private fun onEventClick(event: Event) {
        val i = Intent(this, EventActivity::class.java)
        startActivity(i)
    }


    private fun onUserClick(user: User) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("USER", user)
        intent.putExtra("DISPLAY_TYPE", "VIEW")
        startActivity(intent)
    }
}