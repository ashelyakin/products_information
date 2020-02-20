package com.example.products

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivities
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
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
        vRecView.adapter = RecAdapter(prods.items, savedInstanceState)
        vRecView.layoutManager = LinearLayoutManager(this)

        /*val i = Intent(this, AboutActivity::class.java)
        i.putExtra("name", prods.items[0].name)
        i.putExtra("img", prods.items[0].image)
        i.putExtra("ref", prods.items[0].reference)
        startActivity(i)*/
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

    class RecAdapter(val items: ArrayList<ProductItem>, val savedInstanceState: Bundle?) : RecyclerView.Adapter<RecHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
            val inflater = LayoutInflater.from(parent.context)

            val view = inflater.inflate(R.layout.list_item, parent, false)

            return RecHolder(view, savedInstanceState)
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

    class RecHolder(view: View, val savedInstanceState: Bundle?) : RecyclerView.ViewHolder(view) {

        fun bind(item: ProductItem) {
            val vName = itemView.findViewById<TextView>(R.id.item_name)
            val vId = itemView.findViewById<TextView>(R.id.item_id)
            vName.text = item.name
            vId.text = item.id.toString()

            itemView.setOnClickListener {
                val i = Intent(itemView.context as MainActivity, DescriptionActivity::class.java)
                i.putExtra("name", item.name)
                i.putExtra("id", item.id.toString())
                i.putExtra("desc", item.description)
                i.putExtra("img", item.image)
                i.putExtra("ref", item.reference)
                startActivity(itemView.context, i, savedInstanceState)
            }

        }

    }

}
