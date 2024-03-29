package com.dripr.dripr.fragments.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dripr.dripr.R
import com.dripr.dripr.activities.ArticleActivity
import com.dripr.dripr.activities.PlaceActivity
import com.dripr.dripr.activities.SearchActivity
import com.dripr.dripr.adapters.articles.ArticlesAdapter
import com.dripr.dripr.adapters.places.PlacesAdapter
import com.dripr.dripr.entities.Article
import com.dripr.dripr.entities.Event
import com.dripr.dripr.entities.Place
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.android.synthetic.main.fragment_discover.view.*

class DiscoverFragment : Fragment() {

    private val events = ArrayList<Event>()
    private lateinit var places: List<Place>
    private var TAG = "[DiscoverFragment]"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_discover, container, false)
        populateEventsArray()

        v.searchButton.setOnClickListener {
            startActivity(Intent(this.activity, SearchActivity::class.java))
        }
        return v
    }

    private fun initPlacesRecyclerView(rc: RecyclerView, type: String, data: List<Place>) =
            rc.apply {
                layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = PlacesAdapter(type, data) { place -> onPlaceClick(place) }
            }

    private fun initArticlesRecyclerView(rc: RecyclerView, type: String, data: List<Article>) =
        rc.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ArticlesAdapter(type, data) { article -> onArticleClick(article) }
        }

    private fun onArticleClick(article: Article) {
        val i = Intent(requireContext(), ArticleActivity::class.java)
        i.putExtra("Article", article)
        startActivity(i)
    }

    private fun onPlaceClick(place: Place) {
        val i = Intent(requireContext(), PlaceActivity::class.java)
        i.putExtra("Place", place)
        startActivity(i)
    }

    private fun populateEventsArray() {
        FirebaseFirestore.getInstance().collection("places")
            .get()
            .addOnSuccessListener { documents ->

                val toTryPlaces = ArrayList<Place>()
                val popularPlaces = ArrayList<Place>()

                for (document in documents) {
                    val place = document.toObject(Place::class.java).also { it.id = document.id }
                    if (place.tags.contains("to_try")) {
                        toTryPlaces.add(place)
                    }
                    if (place.tags.contains("popular")) {
                        popularPlaces.add(place)
                    }
                }
                tryRecyclerView.apply {
                    layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = PlacesAdapter("horizontal", toTryPlaces) { place -> onPlaceClick(place) }
                }
                famousRecyclerView.apply {
                    layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
                    adapter = PlacesAdapter("vertical", popularPlaces) { place -> onPlaceClick(place) }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        FirebaseFirestore.getInstance().collection("articles")
            .get()
            .addOnSuccessListener { documents ->

                val listArticles = ArrayList<Article>()

                for (document in documents) {
                    val article =
                        document.toObject(Article::class.java).also { it.id = document.id }
                    listArticles.add(article)
                }

                initArticlesRecyclerView(articlesRecyclerView, "horizontal", listArticles)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

    }
}