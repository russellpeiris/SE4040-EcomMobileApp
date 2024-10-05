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
import androidx.lifecycle.lifecycleScope
import com.ead.mobileapp.api.RetrofitClient
import com.ead.mobileapp.dto.cart.AddToCartRequest
import com.ead.mobileapp.dto.product.FeedbackRequest
import com.ead.mobileapp.models.Product
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

            addProductFeedback(comment, rating)  // Call the feedback function here

            // Clear the rating and comment fields after submission
            ratingBar.rating = 0f
            commentEditText.text.clear()
        }


        addToCartButton.setOnClickListener {
            if (selectedProduct != null) {
                addToCart(selectedProduct)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addToCart(product: Product) {
        val email =
            getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("currentUser", "") ?: ""

        val productRepository = ProductRepository(RetrofitClient.productService)

        lifecycleScope.launch {
            try {
                val response = productRepository.addToCart(email, product)
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

    private fun addProductFeedback(comment: String, rating: Int) {
        val feedbackRequest = FeedbackRequest(
            getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("currentUser", "") ?: "",
            comment,
            rating,
            intent.getSerializableExtra("selectedProduct") as Product
        )

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.productService.addProductFeedback(feedbackRequest)
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@ProductActivity,
                        "Feedback submitted successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@ProductActivity,
                        "Error submitting feedback: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ProductActivity,
                    "Error submitting feedback: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
