package com.example.products

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    lateinit var vRecView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vRecView = findViewById<RecyclerView>(R.id.mainAct_recView)
        val br = BufferedReader(InputStreamReader(resources.openRawResource(R.raw.products_info)))

        var line = br.readLine()
        val sb = StringBuilder()
        while (line!=null)
        {
            sb.append(line)
            line = br.readLine()
        }
        var prods = Gson().fromJson(sb.toString(), Products::class.java)
        for (x in prods.items)
        {
            x.image = resources.getIdentifier(x.image, "drawable", applicationContext.packageName).toString()
        }
        vRecView.adapter = RecAdapter(prods.items)
        vRecView.layoutManager = LinearLayoutManager(this)
    }

    class Products(
        val items: ArrayList<ProductItem> = ArrayList<ProductItem>()
    )

    class ProductItem(
        val id: Int,
        val name: String,
        var image: String,
        val description: String,
        val reference: String
    )

    class RecAdapter(val items: ArrayList<ProductItem>) : RecyclerView.Adapter<RecHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
            val inflater = LayoutInflater.from(parent.context)

            val view = inflater.inflate(R.layout.list_item, parent, false)

            return RecHolder(view)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: RecHolder, position: Int) {
            val item = items[position]

            holder.bind(item)
        }

        override fun getItemViewType(position: Int): Int {
            return super.getItemViewType(position)
        }

    }

    class RecHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: ProductItem) {
            val vName = itemView.findViewById<TextView>(R.id.item_name)
            val vImg = itemView.findViewById<ImageView>(R.id.item_image)
            val vId = itemView.findViewById<TextView>(R.id.item_id)
            vName.text = item.name
            vId.text = item.id.toString()
            vImg.setImageResource(item.image.toInt())
        }

    }

}
