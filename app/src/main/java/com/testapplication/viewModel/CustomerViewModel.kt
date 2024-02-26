package com.testapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testapplication.model.Customer
import com.testapplication.model.CustomerResponse
import com.testapplication.repository.CustomerRepository
import kotlinx.coroutines.launch

class CustomerViewModel : ViewModel() {

    private var _customers = MutableLiveData<CustomerResponse>()
    val customers: LiveData<CustomerResponse> get() = _customers

    // Add search query LiveData
    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> get() = _searchQuery

    init {
        fetchCustomers()
    }

    fun fetchCustomers() : MutableLiveData<CustomerResponse> {
        _customers = CustomerRepository.getCustomers()
        return _customers

    }

    // Function to set search query
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
