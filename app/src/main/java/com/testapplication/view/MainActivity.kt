package com.testapplication.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.testapplication.R
import com.testapplication.adapter.CustomerAdapter
import com.testapplication.databinding.ActivityMainBinding
import com.testapplication.model.Customer
import com.testapplication.viewModel.CustomerViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CustomerAdapter
    private lateinit var viewModel: CustomerViewModel
    private var customersList: MutableList<Customer> = mutableListOf()
    private var currentPage = 1
    private var isLoading = false
    private val pageSize = 10
    private var sortOrder = SortOrder.ASCENDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CustomerViewModel::class.java]
        setupRecyclerView()
        searchView()
        setupSortButtons()
    }

    private fun setupRecyclerView() {
        adapter = CustomerAdapter(customersList)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {
                    loadMoreData()
                }
            }
        })
        fetchData()
    }

    private fun loadMoreData() {
        isLoading = true
        currentPage++
        viewModel.fetchCustomers("", "", currentPage, pageSize).observe(this@MainActivity) { data ->
            isLoading = false
            if (data?.data != null) {
                binding.noDataFound.visibility = View.GONE
                customersList.addAll(data.data.customers)
                adapter.notifyDataSetChanged()
            } else {
                binding.noDataFound.visibility = View.VISIBLE
            }
        }
    }

    private fun fetchData() {
        viewModel.fetchCustomers("", "", currentPage, pageSize).observe(this@MainActivity) { data ->
            if (data?.data != null) {
                binding.noDataFound.visibility = View.GONE
                customersList.addAll(data.data.customers)
                adapter.notifyDataSetChanged()
            } else {
                binding.noDataFound.visibility = View.VISIBLE
            }
        }
    }

    private fun searchView() {
        binding.searchBar.doOnTextChanged { text, _, _, _ ->
            var name = ""
            var number = ""
            if (text.toString().isNumeric()) {
                number = text.toString()
            } else {
                name = text.toString()
            }
            customersList.clear() // Clear the list before performing new search
            currentPage = 1 // Reset page to 1 for new search
            viewModel.fetchCustomers(name, number, currentPage, pageSize)
                .observe(this@MainActivity) { data ->
                    if (data?.data != null) {
                        customersList.addAll(data.data.customers)
                        adapter.notifyDataSetChanged()
                    }
                }
        }
    }

    private fun setupSortButtons() {

        binding.sortAscendingButton.setOnClickListener {
            if (sortOrder != SortOrder.ASCENDING) {
                binding.sortAscendingButton.setImageResource(R.drawable.sort)
                sortOrder = SortOrder.ASCENDING
                sortCustomers()
            } else {
                binding.sortAscendingButton.setImageResource(R.drawable.sort_des)
                sortOrder = SortOrder.DESCENDING
                sortCustomers()
            }
        }

    }

    private fun sortCustomers() {
        customersList.sortBy { it.name } // Change this to your desired sorting field
        if (sortOrder == SortOrder.DESCENDING) {
            customersList.reverse()
        }
        adapter.notifyDataSetChanged()
    }

    enum class SortOrder {
        ASCENDING,
        DESCENDING
    }

    private fun String.isNumeric(): Boolean {
        return this.matches("-?\\d+(\\.\\d+)?".toRegex())
    }
}