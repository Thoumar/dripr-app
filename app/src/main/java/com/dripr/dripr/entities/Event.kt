package com.dripr.dripr.entities

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Event(

    var id: String,
    var infos: Info,
    var location: Location,
    var pictures: Pictures

) : ClusterItem, Parcelable {
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

