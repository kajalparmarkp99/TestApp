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
    init {
        fetchCustomers("", "", 1,10)
    }
    fun fetchCustomers(name: String?, number: String?, pageNo: Int?,pageSize:Int?) : MutableLiveData<CustomerResponse> {
        _customers = CustomerRepository.getCustomers(name,number,pageNo,pageSize)
        return _customers

    }
}
