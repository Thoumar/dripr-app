package com.dripr.dripr.adapters.requests

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dripr.dripr.adapters.requests.viewholders.RequestVerticalViewHolder
import com.dripr.dripr.entities.Request

class RequestsAdapter(
    private val type: String,
    private val list: List<Request>,
    private val click: ((request: Request) -> Unit),
    private val accept: ((request: Request) -> Unit),
    private val deny: ((request: Request) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (type) {
            "vertical" -> RequestVerticalViewHolder(inflater, parent)
            else -> RequestVerticalViewHolder(inflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val request: Request = list[position]

        when (type) {
            "vertical" -> {
                holder as RequestVerticalViewHolder
                holder.bind(request, click, accept, deny)
            }
            else -> {
                holder as RequestVerticalViewHolder
                holder.bind(request, click, accept, deny)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}