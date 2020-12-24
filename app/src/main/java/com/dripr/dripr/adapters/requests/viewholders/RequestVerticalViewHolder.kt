package com.dripr.dripr.adapters.requests.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dripr.dripr.R
import com.dripr.dripr.entities.Request
import com.dripr.dripr.entities.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.request_vertical_item.view.*

class RequestVerticalViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.request_vertical_item, parent, false)) {

    fun bind(
        request: Request,
        click: ((request: Request) -> Unit),
        accept: ((request: Request) -> Unit),
        deny: ((request: Request) -> Unit)
    ) {

        FirebaseFirestore.getInstance().document(request.userPath).get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)


                itemView.userPseudo.text = user?.pseudo
                itemView.requestDate.text = request.date.toString()

                Glide
                    .with(itemView)
                    .load(user?.profilePictureUri)
                    .into(itemView.userProfilePicture)
            }


        itemView.requestCard.setOnClickListener {
            click(request)
        }
        itemView.acceptRequestBtn.setOnClickListener {
            accept(request)
        }
        itemView.denyRequestBtn.setOnClickListener {
            deny(request)
        }
    }
}