package com.dripr.dripr.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Event(
    var id: String,
    var name: String,
    var date: Date,
    var ownerId: String,
) : Parcelable {
    constructor() : this(
        "",
        "Test",
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-12-25 20:00:00"),
        "kldsjhlkjsdhfg"
    )
}