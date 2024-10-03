package com.ead.mobileapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ead.mobileapp.adapters.ProductAdapter
import com.ead.mobileapp.models.Product

class HomeActivity : AppCompatActivity() {

    private lateinit var productList: List<Product>
    private lateinit var filteredList: MutableList<Product>
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Sample product list with categories
        productList = listOf(
            Product("Product 1", "$10", description = "Description","Electronics"),
            Product("Product 2", "$20", description = "Description","Clothing"),
            Product("Product 3", "$30", description = "Description","Home"),
            Product("Product 4", "$40", description = "Description","Electronics"),
            Product("Product 5", "$50", description = "Description","Clothing")
        )

        // Initialize filteredList
        filteredList = productList.toMutableList()

        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(filteredList) { product ->

            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("selectedProduct", product)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        val categorySpinner = findViewById<Spinner>(R.id.categorySpinner)

        // Set up SearchView
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterProducts(newText ?: "", selectedCategory = categorySpinner.selectedItem.toString())
                return true
            }
        })

        // Set up Category Spinner (Dropdown)
        val categories = listOf("All", "Electronics", "Clothing", "Home")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = spinnerAdapter

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filterProducts(searchView.query.toString(), selectedCategory = categories[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                filterProducts(searchView.query.toString(), selectedCategory = "All")
            }
        }



        findViewById<ImageButton>(R.id.cartButton).setOnClickListener{
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.profileButton).setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.ordersButton).setOnClickListener{
            val intent = Intent(this, OrdersActivity::class.java)
            startActivity(intent)
        }
    }

    // Filter products based on search query and selected category
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
