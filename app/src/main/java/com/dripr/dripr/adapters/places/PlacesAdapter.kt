package com.dripr.dripr.adapters.places

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dripr.dripr.entities.Place
import com.thoumar.kebabnomade.adapters.places.viewholders.PlaceVerticalViewHolder
import com.thoumar.kebabnomade.adapters.restaurants.viewholders.PlaceHorizontalViewHolder
import com.thoumar.kebabnomade.adapters.restaurants.viewholders.PlaceMapViewHolder

class PlacesAdapter(
    private val type: String,
    private val list: List<Place>,
    private val click: ((place: Place) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (type) {
            "horizontal" -> PlaceHorizontalViewHolder(inflater, parent)
            "vertical" -> PlaceVerticalViewHolder(inflater, parent)
            "map" -> PlaceMapViewHolder(inflater, parent)
            else -> PlaceVerticalViewHolder(inflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val place: Place = list[position]

        when (type) {
            "horizontal" -> {
                holder as PlaceHorizontalViewHolder
                holder.bind(place, click)
            }
            "vertical" -> {
                holder as PlaceVerticalViewHolder
                holder.bind(place, click)
            }
            "map" -> {
                holder as PlaceMapViewHolder
                holder.bind(place, click)
            }
            else -> {
                holder as PlaceHorizontalViewHolder
                holder.bind(place, click)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}