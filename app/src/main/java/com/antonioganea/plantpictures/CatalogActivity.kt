package com.antonioganea.plantpictures;

import android.view.Menu
import android.view.MenuItem
import android.view.MenuInflater
import android.content.Intent
import android.os.Bundle
import java.util.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager


class CatalogActivity : AppCompatActivity() {
    private lateinit var dataAdapter: DataAdapter
    private var data = ArrayList<String>()
    private var images = HashMap<String, Int>()

    private fun filter(text: String) {
        val filteredData: ArrayList<String> = ArrayList()
        for (item in data) {
            if (item.contains(text, ignoreCase = true)) {
                filteredData.add(item)
            }
        }
        dataAdapter.filterList(filteredData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.catalog_layout)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(applicationContext)
        dataAdapter = DataAdapter(data, images)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = dataAdapter
        DataInitializer.initData(data, images)
        dataAdapter.notifyDataSetChanged()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.catalog

        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.catalog -> true
                R.id.photo -> {
                    startActivity(Intent(applicationContext, PhotoActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.search_bar, menu)

        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)
        val searchView: SearchView = searchItem.getActionView() as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(inputText: String?): Boolean {
                filter(inputText!!)
                return false
            }
        })
        return true
    }
}