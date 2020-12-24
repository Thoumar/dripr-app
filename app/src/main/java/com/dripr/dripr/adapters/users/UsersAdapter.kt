package com.dripr.dripr.adapters.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dripr.dripr.adapters.users.viewholders.UserVerticalViewHolder
import com.dripr.dripr.entities.User

class UsersAdapter(
    private val type: String,
    private val list: List<User>,
    private val click: ((user: User) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (type) {
            "vertical" -> UserVerticalViewHolder(inflater, parent)
            else -> UserVerticalViewHolder(inflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user: User = list[position]

        when (type) {
            "vertical" -> {
                holder as UserVerticalViewHolder
                holder.bind(user, click)
            }
            else -> {
                holder as UserVerticalViewHolder
                holder.bind(user, click)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}