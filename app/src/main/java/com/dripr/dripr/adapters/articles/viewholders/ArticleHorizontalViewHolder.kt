package com.dripr.dripr.adapters.articles.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dripr.dripr.R
import com.dripr.dripr.entities.Article
import kotlinx.android.synthetic.main.article_horizontal_item.view.*

class ArticleHorizontalViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.article_horizontal_item, parent, false)) {

    fun bind(article: Article, click: ((article: Article) -> Unit)) {
        itemView.articleTitle.text = article.title

        Glide
            .with(itemView)
            .load(article.cover)
            .into(itemView.articleCover)

        itemView.articleCard.setOnClickListener {
            click(article)
        }
    }
}