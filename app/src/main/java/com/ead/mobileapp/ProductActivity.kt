package com.ead.mobileapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ead.mobileapp.api.RetrofitClient
import com.ead.mobileapp.models.Product
import com.ead.mobileapp.repositories.ProductRepository

class ProductActivity : BackActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        enableUpNavigation(toolbar)

        // Extract product data passed via the intent
        val selectedProduct = intent.getSerializableExtra("selectedProduct") as? Product

        // Update the UI with product details
        if (selectedProduct != null) {
            findViewById<TextView>(R.id.product_name).text = selectedProduct.name
            findViewById<TextView>(R.id.product_price).text = selectedProduct.price
            findViewById<TextView>(R.id.product_description).text = selectedProduct.description
        }

        // Rating and Comment Section
        val ratingBar = findViewById<RatingBar>(R.id.vendor_rating_bar)
        val commentEditText = findViewById<EditText>(R.id.vendor_comment_edit_text)
        val submitButton = findViewById<Button>(R.id.submit_vendor_rating_button)
        val addToCartButton = findViewById<Button>(R.id.add_to_cart_button)

        // Handle the submission of the rating and comment
        submitButton.setOnClickListener {
            val rating = ratingBar.rating
            val comment = commentEditText.text.toString().trim()

            if (comment.isEmpty()) {
                Toast.makeText(this, "Please enter a comment.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simulate submission of the rating and comment
            Toast.makeText(
                this,
                "Submitted Rating: $rating stars\nComment: $comment",
                Toast.LENGTH_LONG
            ).show()

            // Reset the RatingBar and comment fields
            ratingBar.rating = 0f
            commentEditText.text.clear()
        }

        // Handle "Add to Cart" button click
        addToCartButton.setOnClickListener {
            Toast.makeText(this, "${selectedProduct?.name} added to cart", Toast.LENGTH_SHORT).show()
        }

        // Adjust layout based on system window insets like status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
