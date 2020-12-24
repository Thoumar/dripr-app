package com.dripr.dripr.adapters.events.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dripr.dripr.R
import com.dripr.dripr.entities.Event
import kotlinx.android.synthetic.main.event_vertical_item.view.*

class EventVerticalViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.event_vertical_item, parent, false)) {

    fun bind(event: Event, click: ((event: Event) -> Unit)) {
        itemView.userName.text = event.name
        itemView.userBio.text = event.date.toString()
//
//        Glide
//            .with(itemView)
//            .load(event.cover)
//            .into(itemView.eventCover)

        itemView.eventCard.setOnClickListener {
            click(event)
        }
    }
}