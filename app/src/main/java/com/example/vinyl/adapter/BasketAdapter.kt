package com.example.vinyl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyl.R
import com.example.vinyl.data.database.entities.Product

class BasketAdapter(
    private val products: List<Product>,
    private val onRemoveClick: (Product) -> Unit // callback для удаления
) : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    class BasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvBasketProductName)
        val price: TextView = itemView.findViewById(R.id.tvBasketProductPrice)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemoveFromBasket) // кнопка удаления
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_basket_product, parent, false)
        return BasketViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val product = products[position]
        holder.name.text = product.name
        holder.price.text = "${product.price} руб"

        holder.btnRemove.setOnClickListener {
            onRemoveClick(product) // передаем товар для удаления
        }
    }

    override fun getItemCount() = products.size
}