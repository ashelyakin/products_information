package com.example.products

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DescriptionActivity: AppCompatActivity()  {

    private lateinit var name: String
    private lateinit var id: String
    private lateinit var desc: String
    private lateinit var img: String
    private lateinit var ref: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        //получаем инфу из прошлого активити и заполняем поля
        name = intent.getStringExtra("name")
        id = intent.getStringExtra("id")
        desc = intent.getStringExtra("desc")
        img = intent.getStringExtra("img")
        ref = intent.getStringExtra("ref")
        findViewById<TextView>(R.id.item_name).text = name
        findViewById<TextView>(R.id.item_id).text = id
        findViewById<TextView>(R.id.item_description).text = desc
        Picasso.with(this).load(img.toInt()).into(findViewById<ImageView>(R.id.item_image))
    }

    //кнопка Up
    override fun onResume() {
        super.onResume()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //кнопка "О компании"
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_desc, menu)
        return super.onCreateOptionsMenu(menu)

    }

    //обработка нажатий
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            //на кнопку "О компании"
            R.id.about -> {
                val i = Intent(this, AboutActivity::class.java)
                i.putExtra("name", name)
                i.putExtra("img", img)
                i.putExtra("ref", ref)
                startActivity(i)
            }
            //на кнопку Up
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}