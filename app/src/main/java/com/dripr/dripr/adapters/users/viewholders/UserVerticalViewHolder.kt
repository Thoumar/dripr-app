package com.dripr.dripr.adapters.users.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dripr.dripr.R
import com.dripr.dripr.entities.User
import kotlinx.android.synthetic.main.user_vertical_item.view.*

class UserVerticalViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.user_vertical_item, parent, false)) {

    fun bind(user: User, click: ((user: User) -> Unit)) {
        itemView.userEmail.text = user.email
        itemView.userName.text = user.bio

        Glide
            .with(itemView)
            .load(user.profilePictureUri)
            .into(itemView.userProfilePicture)

        itemView.userCard.setOnClickListener {
            click(user)
        }
    }
}