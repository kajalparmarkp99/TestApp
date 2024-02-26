package com.testapplication.model

data class CustomerResponse(
    val success: Boolean,
    val date: String,
    var data: CustomerData
)

data class CustomerData(
    val count: Int,
    val customers: List<Customer>
)

