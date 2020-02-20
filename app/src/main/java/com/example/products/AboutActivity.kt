package com.example.products

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class AboutActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val name = intent.getStringExtra("name")
        val img = intent.getStringExtra("img")
        val ref = intent.getStringExtra("ref")
        findViewById<TextView>(R.id.item_name).text = name
        findViewById<TextView>(R.id.item_ref).text = ref
        Picasso.with(this).load(img.toInt()).into(findViewById<ImageView>(R.id.item_image))
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
}