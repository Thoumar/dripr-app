package com.dripr.dripr.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dripr.dripr.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private var TAG = "[SignUpActivity]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        validateButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordOneInput.text.toString()

            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = Firebase.auth.currentUser
                        Toast.makeText(baseContext, user.toString(), Toast.LENGTH_LONG).show()

                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("USER", user)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }
}