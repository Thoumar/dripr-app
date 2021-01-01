package com.dripr.dripr.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dripr.dripr.R
import com.dripr.dripr.entities.Article

class ArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val article = intent.getParcelableExtra<Article>("ARTICLE")
    }
}