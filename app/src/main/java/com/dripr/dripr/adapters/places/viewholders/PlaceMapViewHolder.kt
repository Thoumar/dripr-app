package com.thoumar.kebabnomade.adapters.restaurants.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dripr.dripr.R
import com.dripr.dripr.entities.Place
import kotlinx.android.synthetic.main.place_map_item.view.*


class PlaceMapViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.place_map_item, parent, false)) {

    fun bind(place: Place, click: ((place: Place) -> Unit)) {
        itemView.placeName.text = place.name
        itemView.placeAddress.text = place.address

        Glide
            .with(itemView)
            .load(place.cover)
            .into(itemView.placeCover)

        itemView.placeCard.setOnClickListener {
            click(place)
        }
    }
}