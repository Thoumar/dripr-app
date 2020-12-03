package com.dripr.dripr.entities

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Place(

    var id: String,
    var name: String,
    var cover: String,
    var short: String,
    var description: String,
    var address: String,
    var latitude: Double,
    var longitude: Double,
    var isVerified: Boolean,
    var website: String,
    var tags: ArrayList<String>,
    var range: Double?

) : ClusterItem, Parcelable {

    constructor() : this(
        "place_id",
        "TOMYUM KUNGFU",
        "TOMYUM KUNGFU",
        "Un restaurant chinois à Paris 8ème",
        "Description longe du lieu",
        "40 rue des primeveres MDR",
        42.03156513221,
        2.03156513221,
        true,
        "https://www.restaurant-lile.com/",
        ArrayList(emptyList<String>()),
        0.0
    ) {
        val tags = ArrayList<String>()
        tags.add("popular")
        this.tags = tags
    }

    override fun getPosition(): LatLng {
        return LatLng(latitude, longitude)
    }

    override fun getTitle(): String? {
        return this.name
    }

    override fun getSnippet(): String? {
        return this.short
    }

}

