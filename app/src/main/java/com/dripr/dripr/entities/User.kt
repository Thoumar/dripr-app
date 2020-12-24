package com.dripr.dripr.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: String,
    var pseudo: String,
    var bio: String,
    var profilePictureUri: String,
    var email: String
) : Parcelable {
    constructor() : this(
        "",
        "dripr@dripr.com",
        "dripr@dripr.com",
        "dripr@dripr.com",
        "kldsjhlkjsdhfg"
    )
}