package com.dripr.dripr.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dripr.dripr.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*

class SignUpActivity : AppCompatActivity() {

    private var TAG = "[SignUpActivity]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        validateButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordOneInput.text.toString()
            val confirmPassword = passwordTwoInput.text.toString()
            if (password === confirmPassword) {
                Firebase.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = Firebase.auth.currentUser
                            val db = FirebaseFirestore.getInstance()
                            db.collection("users")
                                .whereEqualTo("email", user!!.email)
                                .get()
                                .addOnSuccessListener { documents ->
                                    if (documents.size() >= 1) {
                                        startActivity(Intent(this, MainActivity::class.java))
                                    } else {
                                        val defaultUserId =
                                            user.email + (Random().nextInt(900000) + 100000).toString()
                                        val newUserData = hashMapOf(
                                            "profilePictureUri" to user.photoUrl.toString(),
                                            "email" to user.email,
                                            "bio" to "New user of the Dripr App",
                                            "favoritePlaces" to null
                                        )

                                        val sharedPref = getPreferences(Context.MODE_PRIVATE)
                                        with(sharedPref.edit()) {
                                            putString("USER_UID", user.uid)
                                            apply()
                                        }


                                        db.collection("users").document(defaultUserId)
                                            .set(newUserData)
                                            .addOnSuccessListener { document ->
                                                startActivity(
                                                    Intent(
                                                        this,
                                                        MainActivity::class.java
                                                    )
                                                )
                                            }
                                            .addOnFailureListener { e ->
                                                // Handle error
                                            }
                                    }
                                }
                                .addOnFailureListener { exception ->

                                }

                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
            } else {
                passwordOneInput.error = "Les mots de passe ne correspondent pas"
                passwordTwoInput.error = ""
            }
        }
    }
}