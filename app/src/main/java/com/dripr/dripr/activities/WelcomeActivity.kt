package com.dripr.dripr.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dripr.dripr.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_welcome.*
import java.util.*


class WelcomeActivity : AppCompatActivity() {

    private var TAG = "[WelcomeActivity]"
    private val RC_GOOGLE_SIGN_IN: Int = 1
    private val RC_FACEBOOK_SIGN_IN: Int = 2
    private lateinit var callbackManager: CallbackManager

    public override fun onStart() {
        super.onStart()

        val currentUser = Firebase.auth.currentUser

        if (currentUser !== null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Google sign client
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Facebook Login
        callbackManager = CallbackManager.Factory.create()

        // Callback registration
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    // App code
                    handleFacebookAccessToken(loginResult!!.accessToken)
                }

                override fun onCancel() {
                    Toast.makeText(baseContext, "Login Cancel", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: FacebookException) {
                    Toast.makeText(baseContext, exception.message, Toast.LENGTH_LONG).show()
                }
            })

        // Listeners
        goToRegisterBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        goToGoogleBtn.setOnClickListener {
            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
            startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
        }

        goToFacebookBtn.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Facebook
        callbackManager.onActivityResult(requestCode, resultCode, data)

        // Google
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            validateLogin(task)
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        Firebase.auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            validateLogin(task)
        }
    }

    private fun validateLogin(task: Task<AuthResult>) {
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
                        val defaultUserId = Firebase.auth.currentUser!!.uid
                        val newUserData = hashMapOf(
                            "profilePictureUri" to user.photoUrl.toString(),
                            "email" to user.email,
                            "pseudo" to user.email + (Random().nextInt(900000) + 100000).toString(),
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
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener { e ->
                                // Handle error
                            }
                    }
                }
                .addOnFailureListener { exception ->

                }
        } else {
            Snackbar.make(
                welcome_activity_container,
                "Authentication Failed.",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}