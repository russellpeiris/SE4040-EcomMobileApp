package com.ead.mobileapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ead.mobileapp.R
import com.ead.mobileapp.adapters.ProductAdapter
import com.ead.mobileapp.api.RetrofitClient
import com.ead.mobileapp.models.Product
import com.ead.mobileapp.repositories.ProductRepository
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private var productList: List<Product>  = emptyList()
    private lateinit var filteredList: MutableList<Product>
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        filteredList = mutableListOf()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(filteredList) { product ->
            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("selectedProduct", product)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterProducts(newText ?: "", selectedCategory = findViewById<Spinner>(R.id.categorySpinner).selectedItem.toString())
                return true
            }
        })

        val categories = listOf("All", "Electronics", "Clothing", "Home")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val categorySpinner = findViewById<Spinner>(R.id.categorySpinner)
        categorySpinner.adapter = spinnerAdapter

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filterProducts(searchView.query.toString(), selectedCategory = categories[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                filterProducts(searchView.query.toString(), selectedCategory = "All")
            }
        }

        // Navigation buttons
        findViewById<ImageButton>(R.id.cartButton).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        findViewById<ImageButton>(R.id.profileButton).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        findViewById<ImageButton>(R.id.ordersButton).setOnClickListener {
            startActivity(Intent(this, OrdersActivity::class.java))
        }

        fetchProducts()
    }

    private fun fetchProducts() {
        lifecycleScope.launch {
            val productService = RetrofitClient.productService
            val productRepository = ProductRepository(productService)

            try {
                productList = productRepository.getProducts() ?: emptyList()
                filteredList.clear()
                filteredList.addAll(productList)
                adapter.notifyDataSetChanged()

                filterProducts("", selectedCategory = "All") // Display all products

            } catch (e: Exception) {
                println( "********************* "+e.message)
                Toast.makeText(this@HomeActivity, "Error fetching products: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchProducts()
    }

    private fun filterProducts(query: String, selectedCategory: String) {
        val filteredBySearch = productList.filter {
            it.name.contains(query, ignoreCase = true)
        }

        filteredList.clear()
        filteredList.addAll(
            if (selectedCategory == "All") {
                filteredBySearch
            } else {
                filteredBySearch.filter { it.category == selectedCategory }
            }
        )
        adapter.notifyDataSetChanged()
    }
}
