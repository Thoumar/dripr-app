package com.dripr.dripr.entities

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Place(

    var id: String,
    var infos: Info,
    var location: Location,
    var pictures: Pictures

) : ClusterItem, Parcelable {

    constructor() : this(
        "place_id",
        Info(
            "__url__picture",
            "TOMYUM KUNGFU",
            "Un restaurant chinois à Paris 8ème",
            "__website_uri__",
            "Lorem ipsum dolor sit amet, consecttur adipi cing elit, sed do eiusmod tempor didunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consecttur adipi ",
            17.0,
            Type.Bar,
            hashMapOf(
                "monday" to "11:30 - 23:00",
                "tuesday" to "11:30 - 23:00",
                "wednesday" to "11:30 - 23:00",
                "tuesday" to "11:30 - 23:00",
                "friday" to "11:30 - 23:00",
                "saturday" to "11:30 - 23:00",
                "sunday" to "11:30 - 23:00",
                "global" to "11:30 - 23:00",
                "openOnWeekends" to "yes",
                "openOnDayOff" to "yes"
            )
        ),
        Location(
            42.03156513221,
            2.03156513221,
            2.5,
            "Ile de France",
            "40 rue des primevères"
        ),
        Pictures(
            arrayListOf(
                "__url_image_one__",
                "__url_image_two__",
                "__url_image_three__",
                "__url_image_four__",
                "__url_image_five__",
                "__url_image_six__",
                "__url_image_seven__",
            )
        )
    )

    enum class Type {
        Bar
    }

    @Parcelize
    class Info(
        var cover: String,
        var name: String,
        var snippet: String,
        var websiteUri: String,
        var description: String,
        var averagePrice: Double,
        var type: Type,
        var hours: HashMap<String, String>
    ) : Parcelable

    @Parcelize
    class Location(
        var latitude: Double,
        var longitude: Double,
        var range: Double?,
        var region: String,
        var streetName: String,
    ) : Parcelable

    @Parcelize
    enum class Hour(
        var startTime: String,
        var endTime: String
    ) : Parcelable

    @Parcelize
    class Pictures(
        var pictures: ArrayList<String>
    ) : Parcelable


    override fun getPosition(): LatLng {
        return LatLng(location.latitude, location.longitude)
    }

    override fun getTitle(): String {
        return this.infos.name
    }

    override fun getSnippet(): String {
        return this.infos.snippet
    }
}

