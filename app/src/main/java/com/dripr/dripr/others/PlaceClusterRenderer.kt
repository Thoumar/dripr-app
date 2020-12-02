package com.thoumar.kebabnomade.others

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.dripr.dripr.R
import com.dripr.dripr.entities.Place
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class PlaceClusterRenderer(
    val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Place>
) : DefaultClusterRenderer<Place>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(place: Place, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(place, markerOptions)

        val decodedRessource =
            BitmapFactory.decodeResource(context.resources, R.drawable.ic_marker_icon)
        val bitmap = Bitmap.createScaledBitmap(decodedRessource, 60, 60, false)
        val icon: BitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap)

        markerOptions.icon(icon)
    }
}