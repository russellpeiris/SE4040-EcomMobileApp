package com.ead.mobileapp.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ead.mobileapp.R
import com.ead.mobileapp.models.CartItem

class CartAdapter(private val cartItems: List<CartItem>, private val onRemoveClick: (CartItem) -> Unit) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.bind(item, onRemoveClick)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        private val category: TextView = itemView.findViewById(R.id.productCategory)
        private val removeButton: ImageButton = itemView.findViewById(R.id.removeButton)


        fun bind(cartItem: CartItem, onRemoveClick: (CartItem) -> Unit) {
            productName.text = cartItem.name
            productPrice.text = "$${cartItem.price}"
            category.text = "Quantity: ${cartItem.category}"
            Glide.with(itemView)
                .load(cartItem.imageUrl)
                .into(itemView.findViewById<ImageView>(R.id.productImage))

            // Handle Remove Button click
            removeButton.setOnClickListener {
                onRemoveClick(cartItem)
            }
        }
    }
}

