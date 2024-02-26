package com.testapplication.model
data class Customer(
    val id: String,
    val cgId: Int,
    val name: String,
    val mobile: String,
    val dialCode: String?,
    val email: String?,
    val recordStatus: Boolean,
    val createdAt: String,
    val updatedAt: String
)
