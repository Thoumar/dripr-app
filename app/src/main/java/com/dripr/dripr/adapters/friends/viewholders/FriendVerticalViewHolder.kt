package com.dripr.dripr.adapters.friends.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dripr.dripr.R
import com.dripr.dripr.entities.Friend
import com.dripr.dripr.entities.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.friend_vertical_item.view.*

class FriendVerticalViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.friend_vertical_item, parent, false)) {

    fun bind(friend: Friend, click: ((friend: Friend) -> Unit)) {

        FirebaseFirestore.getInstance().document(friend.userPath).get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)

                itemView.userPseudo.text = user?.pseudo

                val options = RequestOptions
                    .circleCropTransform()
                    .placeholder(R.drawable.ic_profile)

                Glide
                    .with(itemView)
                    .load(friend.profilePicture)
                    .apply(options)
                    .into(itemView.userProfilePicture)
            }


        itemView.friendCard.setOnClickListener {
            click(friend)
        }
    }
}