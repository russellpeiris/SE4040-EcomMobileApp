package com.ead.mobileapp.activities

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
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ead.mobileapp.R
import com.ead.mobileapp.api.RetrofitClient
import com.ead.mobileapp.models.FeedBack
import com.ead.mobileapp.models.Product
import com.ead.mobileapp.repositories.CartRepository
import com.ead.mobileapp.repositories.ProductRepository
import kotlinx.coroutines.launch

class ProductActivity : BackActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        enableUpNavigation(toolbar)

        val selectedProduct = intent.getSerializableExtra("selectedProduct") as? Product

        if (selectedProduct != null) {
            findViewById<TextView>(R.id.product_name).text = selectedProduct.name
            findViewById<TextView>(R.id.product_price).text = selectedProduct.price
            findViewById<TextView>(R.id.product_description).text = selectedProduct.description
            Glide.with(this)
                .load(selectedProduct.imageUrl)
                .into(findViewById(R.id.product_image))
        }

        val ratingBar = findViewById<RatingBar>(R.id.vendor_rating_bar)
        val commentEditText = findViewById<EditText>(R.id.vendor_comment_edit_text)
        val submitButton = findViewById<Button>(R.id.submit_vendor_rating_button)
        val addToCartButton = findViewById<Button>(R.id.add_to_cart_button)

        submitButton.setOnClickListener {
            val rating = ratingBar.rating.toInt()
            val comment = commentEditText.text.toString().trim()

            if (comment.isEmpty()) {
                Toast.makeText(this, "Please enter a comment.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            addProductFeedback(
                getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("currentUserEmail", "") ?: "",
                selectedProduct?._id ?: "",
                comment,
                rating
            )

            ratingBar.rating = 0f
            commentEditText.text.clear()
        }


        addToCartButton.setOnClickListener {
            if (selectedProduct != null) {
                addToCart(selectedProduct)
            }
        }

        getFeedback(selectedProduct?._id ?: "", getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("currentUserEmail", "") ?: "")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addToCart(product: Product) {
        val email =
            getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("currentUserEmail", "") ?: ""

       val cartRepository = CartRepository(RetrofitClient.cartService)

        lifecycleScope.launch {
            try {
                val response = cartRepository.addToCart(email, product)
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@ProductActivity,
                        "Added to cart successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@ProductActivity,
                        "Error adding to cart: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (
                e: Exception
            ) {
                Toast.makeText(
                    this@ProductActivity,
                    "Error adding to cart: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show(
                )

            }

        }
    }

    private fun addProductFeedback(
        email: String,
        productId: String,
        comment: String,
        rating: Int
    ) {
        val productFeedBack = FeedBack(email, productId, comment, rating)
        val productRepository = ProductRepository(RetrofitClient.productService)
        lifecycleScope.launch {
            try {
                val response = productRepository.addProductFeedback(productFeedBack)
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@ProductActivity,
                        "Feedback added successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@ProductActivity,
                        "Error adding feedback: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (
                e: Exception
            ) {
                Toast.makeText(
                    this@ProductActivity,
                    "Error adding feedback: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show(
                )

            }

        }
    }

    private fun getFeedback(productId: String, email: String) {
        val productRepository = ProductRepository(RetrofitClient.productService)
        lifecycleScope.launch {
            try {
                val response = productRepository.getProductFeedback(productId, email)
                if (response != null) {
                    findViewById<RatingBar>(R.id.vendor_rating_bar).rating = response.rating.toFloat()
                    findViewById<EditText>(R.id.vendor_comment_edit_text).setText(response.comment)
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProductActivity, "Error fetching feedback: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }

}
