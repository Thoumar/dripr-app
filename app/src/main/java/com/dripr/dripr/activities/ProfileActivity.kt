package com.dripr.dripr.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dripr.dripr.R
import com.dripr.dripr.adapters.friends.FriendsAdapter
import com.dripr.dripr.adapters.requests.RequestsAdapter
import com.dripr.dripr.entities.Friend
import com.dripr.dripr.entities.Request
import com.dripr.dripr.entities.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*
import java.text.SimpleDateFormat


class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val user = intent.getParcelableExtra<User>("USER")
        val displayType: String = intent.getStringExtra("DISPLAY_TYPE") ?: "VIEW"

        val options = RequestOptions
            .circleCropTransform()
            .placeholder(R.drawable.ic_profile)


        if (displayType == "VIEW") {

            addAsFriendBtn.visibility = View.VISIBLE
            onBoardingBtn.visibility = View.GONE
            logOutBtn.visibility = View.GONE

            Glide
                .with(this)
                .asBitmap()
                .load(user?.profilePictureUri)
                .apply(options)
                .into(profilePicture)

            addAsFriendBtn.setOnClickListener {
                FirebaseFirestore.getInstance().document("users/" + Firebase.auth.currentUser!!.uid)
                    .get().addOnSuccessListener { document ->

                    val me = document.toObject(User::class.java).also { it?.id = document.id }

                    val requestData = hashMapOf(
                        "userPath" to "users/" + me?.id,
                        "profilePicture" to me?.profilePictureUri,
                        "pseudo" to me?.pseudo,
                        "date" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-12-25 20:00:00"),
                    )

                    FirebaseFirestore.getInstance().collection("users").document(user!!.id)
                        .collection("requests").add(requestData).addOnSuccessListener {
                        Toast.makeText(this, "Invitation envoyée", Toast.LENGTH_LONG).show()
                        addAsFriendBtn.text = "Envoyée"
                        addAsFriendBtn.isEnabled = false
                    }.addOnFailureListener { e -> }
                }
            }
        } else if (displayType == "EDIT") {

            FirebaseFirestore.getInstance().collection("users")
                .document(Firebase.auth.currentUser!!.uid).collection("requests")
                .get()
                .addOnSuccessListener { documents ->
                    val requests = ArrayList<Request>()
                    for (document in documents) {
                        val request =
                            document.toObject(Request::class.java).also { it.id = document.id }
                        requests.add(request)
                    }


                    requestsRequestsRc.apply {
                        layoutManager =
                            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                        adapter = RequestsAdapter("vertical", requests,
                            { request -> onRequestClick(request) },
                            { request -> onAcceptClick(request) },
                            { request -> onDenyClick(request) }
                        )
                    }
                }

            FirebaseFirestore.getInstance().collection("users")
                .document(Firebase.auth.currentUser!!.uid).collection("friends")
                .get()
                .addOnSuccessListener { documents ->
                    val friends = ArrayList<Friend>()
                    for (document in documents) {
                        val friend =
                            document.toObject(Friend::class.java).also { it.id = document.id }
                        friends.add(friend)
                    }


                    friendsRc.apply {
                        layoutManager =
                            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                        adapter =
                            FriendsAdapter("vertical", friends) { friend -> onFriendClick(friend) }
                    }
                }

            Toast.makeText(applicationContext, displayType, Toast.LENGTH_LONG).show()
            addAsFriendBtn.visibility = View.GONE

            Glide
                .with(this)
                .asBitmap()
                .load(user?.profilePictureUri)
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

    private fun onFriendClick(friend: Friend) {
        FirebaseFirestore.getInstance().document(friend.userPath).get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java).also { it?.id = document.id }
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("USER", user)
                intent.putExtra("DISPLAY_TYPE", "VIEW")
                startActivity(intent)
            }
    }

    private fun onRequestClick(request: Request) {
        FirebaseFirestore.getInstance().document(request.userPath).get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java).also { it?.id = document.id }
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("USER", user)
                intent.putExtra("DISPLAY_TYPE", "VIEW")
                startActivity(intent)
            }
    }

    private fun onAcceptClick(request: Request) {
        // For the actual user of the app
        val newFriendData = hashMapOf(
            "userPath" to request.userPath,
            "date" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-12-25 20:00:00"),
        )

        // For the initiator of the request
        val friendData = hashMapOf(
            "userPath" to "users/" + Firebase.auth.currentUser!!.uid,
            "date" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-12-25 20:00:00"),
        )

        // Add friend to user's friends
        FirebaseFirestore.getInstance().document("users/" + Firebase.auth.currentUser!!.uid)
            .collection("friends").add(newFriendData)
        // Add friend to initiator's friends
        FirebaseFirestore.getInstance().document(request.userPath).collection("friends")
            .add(friendData)
        // Remove
        FirebaseFirestore.getInstance().document("users/" + Firebase.auth.currentUser!!.uid)
            .collection("requests").document(request.id).delete()
        requestsRequestsRc.adapter?.notifyDataSetChanged()
    }


    private fun onDenyClick(request: Request) {
        // Remove friend request from both profiles
        FirebaseFirestore.getInstance().document("users/" + Firebase.auth.currentUser!!.uid)
            .collection("requests").document(request.id).delete()
        requestsRequestsRc.adapter?.notifyDataSetChanged()
    }
}