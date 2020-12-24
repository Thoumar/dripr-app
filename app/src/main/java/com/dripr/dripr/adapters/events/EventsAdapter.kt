package com.dripr.dripr.adapters.events.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dripr.dripr.entities.Event

class EventsAdapter(
    private val type: String,
    private val list: List<Event>,
    private val click: ((Event: Event) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (type) {
            "horizontal" -> EventHorizontalViewHolder(inflater, parent)
            "vertical" -> EventVerticalViewHolder(inflater, parent)
            else -> EventVerticalViewHolder(inflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val event: Event = list[position]

        when (type) {
            "horizontal" -> {
                holder as EventHorizontalViewHolder
                holder.bind(event, click)
            }
            "vertical" -> {
                holder as EventVerticalViewHolder
                holder.bind(event, click)
            }
            else -> {
                holder as EventVerticalViewHolder
                holder.bind(event, click)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}