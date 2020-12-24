package com.dripr.dripr.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Request(
    var id: String,
    var userPath: String,
    var date: Date
) : Parcelable {
    constructor() : this(
        "",
        "users/userid",
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-12-25 20:00:00"),
    )
}