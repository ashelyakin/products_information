package com.example.products

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    lateinit var vRecView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vRecView = findViewById<RecyclerView>(R.id.mainAct_recView)

        //читаем содержимое .json и записываем в sb как строку
        val br = BufferedReader(InputStreamReader(resources.openRawResource(R.raw.products_info)))
        var line = br.readLine()
        val sb = StringBuilder()
        while (line!=null)
        {
            sb.append(line)
            line = br.readLine()
        }

        //парсим sb, получаем экземпляр специально созданного класса Products
        var prods = Gson().fromJson(sb.toString(), Products::class.java)

        //конвертируем строковое представление ресурса, в котором содержится изображение для продукта, в id этого ресурса
        //и сохраняем как строку
        for (x in prods.items)
        {
            x.image = resources.getIdentifier(x.image, "drawable", applicationContext.packageName).toString()
        }

        //назначаем адаптер и layout manager для нашего RecyclerView
        vRecView.adapter = RecAdapter(prods.items, savedInstanceState)
        vRecView.layoutManager = LinearLayoutManager(this)

    }

    //класс для хранения информации из .json
    class Products(
        val items: ArrayList<ProductItem> = ArrayList<ProductItem>()
    )

    //для Products
    class ProductItem(
        val id: Int,
        val name: String,
        var image: String,
        val description: String,
        val reference: String
    )

    //адаптер для нашего RecyclerView
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

    //холдер для адаптера
    class RecHolder(view: View, val savedInstanceState: Bundle?) : RecyclerView.ViewHolder(view) {

        fun bind(item: ProductItem) {
            //заполняем поля
            val vName = itemView.findViewById<TextView>(R.id.item_name)
            val vId = itemView.findViewById<TextView>(R.id.item_id)
            vName.text = item.name
            vId.text = item.id.toString()

            //обрабатываем нажатие на элемент
            itemView.setOnClickListener {
                val i = Intent(itemView.context as MainActivity, DescriptionActivity::class.java)
                i.putExtra("name", item.name)
                i.putExtra("id", item.id.toString())
                i.putExtra("desc", item.description)
                i.putExtra("img", item.image)
                i.putExtra("ref", item.reference)
                itemView.context.startActivity(i)
            }

        }

    }

}
