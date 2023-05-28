package com.example.recipeapp.repository

import com.example.recipeapp.data.Customer
import com.example.recipeapp.data.CustomerDao
import kotlinx.coroutines.flow.Flow

class CustomerRepository(private val customerDao: CustomerDao) {

    suspend fun insertCustomer(customer: Customer){
        customerDao.insertCustomer(customer)
    }

    fun getCustomer(customer: String, password: String): Flow<Customer> {
        return customerDao.getCustomer(customer, password)
    }
}