package com.thoumar.kebabnomade.adapters.restaurants.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dripr.dripr.R
import com.dripr.dripr.entities.Place
import kotlinx.android.synthetic.main.place_horizontal_item.view.*

class PlaceHorizontalViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.place_horizontal_item, parent, false)) {

    fun bind(place: Place, click: ((place: Place) -> Unit)) {
        itemView.placeName.text = place.infos.name

        Glide
            .with(itemView)
            .load(place.infos.cover)
            .into(itemView.placeCover)

        itemView.placeCard.setOnClickListener {
            click(place)
        }
    }
}