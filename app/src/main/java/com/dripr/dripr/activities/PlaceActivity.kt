package com.dripr.dripr.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dripr.dripr.R
import com.dripr.dripr.entities.Place
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_place.*

class PlaceActivity : AppCompatActivity() {

    private var place: Place? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

        place = intent.getParcelableExtra("Place")

        carouselView.pageCount = place?.pictures!!.size
        carouselView.setImageListener(imageListener)
    }


    var imageListener: ImageListener = ImageListener { position, imageView ->
        Glide
            .with(baseContext)
            .load(place?.pictures!![position]["src"])
            .into(imageView)
    }
}