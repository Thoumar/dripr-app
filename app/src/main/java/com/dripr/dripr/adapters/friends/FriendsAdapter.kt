package com.dripr.dripr.adapters.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dripr.dripr.adapters.friends.viewholders.FriendVerticalViewHolder
import com.dripr.dripr.entities.Friend

class FriendsAdapter(
    private val type: String,
    private val list: List<Friend>,
    private val click: ((friend: Friend) -> Unit),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (type) {
            "vertical" -> FriendVerticalViewHolder(inflater, parent)
            else -> FriendVerticalViewHolder(inflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val friend: Friend = list[position]

        when (type) {
            "vertical" -> {
                holder as FriendVerticalViewHolder
                holder.bind(friend, click)
            }
            else -> {
                holder as FriendVerticalViewHolder
                holder.bind(friend, click)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}