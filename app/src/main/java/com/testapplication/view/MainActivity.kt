package com.testapplication.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.testapplication.R
import com.testapplication.adapter.CustomerAdapter
import com.testapplication.databinding.ActivityMainBinding
import com.testapplication.model.Customer
import com.testapplication.viewModel.CustomerViewModel
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CustomerAdapter
    private lateinit var viewModel: CustomerViewModel
    private var customersList: List<Customer> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CustomerViewModel::class.java]
        setupRecyclerView()
    }
    private fun setupRecyclerView() {
        viewModel.fetchCustomers().observe(this@MainActivity) { data ->
            customersList = data.data.customers
            adapter.updateData(customersList)
            if (customersList.isEmpty()) {
                binding.noDataFound.visibility = View.VISIBLE
            } else {
                binding.noDataFound.visibility = View.GONE
            }
        }
        adapter = CustomerAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
    }




}