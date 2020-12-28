package com.dripr.dripr.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dripr.dripr.R
import com.dripr.dripr.adapters.friends.FriendsAdapter
import com.dripr.dripr.entities.Friend
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_friends_picker.*


class FriendsPickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_picker)

        val selectedFriends = ArrayList<Friend>()

        friendsActionButton.setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putParcelableArrayListExtra("SELECTED_FRIENDS", selectedFriends)
            setResult(RESULT_OK, returnIntent)
            finish()
        }

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(Firebase.auth.currentUser?.uid.toString())
            .collection("friends")
            .get()
            .addOnSuccessListener { documents ->
                val friends = ArrayList<Friend>()
                for (document in documents) {
                    val friend =
                        document.toObject(Friend::class.java).also {
                            it.id = document.id
                            it.isSelected = false
                        }
                    friends.add(friend)
                }

                friendsRc.apply {
                    layoutManager =
                        LinearLayoutManager(
                            this@FriendsPickerActivity.applicationContext,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    adapter = FriendsAdapter(
                        "picker",
                        friends
                    ) { friend ->
                        if (friend.isSelected) {
                            selectedFriends.add(friend)
                        } else {
                            selectedFriends.filter { it.id === friend.id }
                        }
                    }
                }
            }
    }
}