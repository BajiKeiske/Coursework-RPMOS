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
    private val products: List<Pair<Product, Int>>, // Product + количество
    private val onRemoveClick: (Product) -> Unit
) : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    class BasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvBasketProductName)
        val price: TextView = itemView.findViewById(R.id.tvBasketProductPrice)
        val quantity: TextView = itemView.findViewById(R.id.tvQuantity) // новое поле
        val btnRemove: Button = itemView.findViewById(R.id.btnRemoveFromBasket)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_basket_product, parent, false)
        return BasketViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val (product, quantity) = products[position]
        holder.name.text = product.name
        holder.price.text = "${product.price * quantity} руб"
        holder.quantity.text = "Количество: $quantity" // показываем количество

        holder.btnRemove.setOnClickListener {
            onRemoveClick(product)
        }
    }

    override fun getItemCount() = products.size
}