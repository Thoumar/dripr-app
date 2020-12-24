package com.dripr.dripr.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dripr.dripr.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.activity_create_event.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CreateEventActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    val TAG = "[CreateEventActivity]"
    var eventDate: String? = null
    var eventTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        dateTextView.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd: DatePickerDialog = DatePickerDialog.newInstance(
                    this@CreateEventActivity,
                    now[Calendar.YEAR],
                    now[Calendar.MONTH],
                    now[Calendar.DAY_OF_MONTH]
            )

            dpd.show(supportFragmentManager, "ok")
        }

        timeTextView.setOnClickListener {
            val tpd: TimePickerDialog? = TimePickerDialog.newInstance(
                    this@CreateEventActivity, true
            )

            tpd?.show(supportFragmentManager, "ok")
        }

        createEvent.setOnClickListener {
            val event_name = userName.text.toString()
            val DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss"

            val formatter: DateFormat = SimpleDateFormat(DEFAULT_PATTERN)
            val event_date = formatter.parse("${eventDate} ${eventTime}")

            val newEventData = hashMapOf(
                "name" to event_name,
                "date" to event_date,
                "ownerId" to Firebase.auth.currentUser?.uid
            )

            FirebaseFirestore.getInstance().collection("events")
                    .add(newEventData)
                    .addOnSuccessListener {
                        Toast.makeText(
                                baseContext,
                                "DocumentSnapshot successfully written!",
                                Toast.LENGTH_LONG
                        ).show()
                        Log.d(TAG, "DocumentSnapshot successfully written!")
                        finish()
                    }.addOnFailureListener { e ->
                        Toast.makeText(baseContext, "Error writing document", Toast.LENGTH_LONG).show()
                        Log.w(TAG, "Error writing document", e)
                    }
        }
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        eventDate = "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth
        dateTextView.text = eventDate
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        eventTime = "$hourOfDay:$minute:$second"
        timeTextView.text = eventTime
    }
}