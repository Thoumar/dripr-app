package com.dripr.dripr.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
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

        placeName.text = place?.name
        placeAddress.text = place?.address


        addressContainer.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=${place?.latitude},${place?.longitude} (" + place?.name + ")")
            )
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }
        placeShort.loadData(
            "<html><body>" + place?.short + "</body></html>",
            "text/html; charset=utf-8",
            "utf-8"
        )
        placeDescription.loadData(
            "<html><body>" + place?.description + "</body></html>",
            "text/html; charset=utf-8",
            "utf-8"
        )
        placeWebsite.loadUrl(place!!.website)

        carouselView.pageCount = place?.pictures!!.size
        carouselView.setImageListener(imageListener)
    }


    var imageListener: ImageListener = ImageListener { position, imageView ->
        Glide
            .with(baseContext)
            .load(place?.pictures!![position]["src"])
            .into(imageView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}