package com.example.recipeapp.viewModels

import androidx.lifecycle.*
import com.example.recipeapp.data.Customer
import com.example.recipeapp.data.CustomerDao
import com.example.recipeapp.repository.CustomerRepository
import com.example.recipeapp.repository.RecipeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class CustomerViewModel(private val customerDao: CustomerDao) : ViewModel() {

    private lateinit var repository: CustomerRepository

    init {
        viewModelScope.launch {
            repository = CustomerRepository(customerDao)
        }
    }

    fun addNewCustomer(email: String, password: String) {
        val newCustomer = getNewCustomerEntry(email, password)
        insertCustomer(newCustomer)
    }
    private fun getNewCustomerEntry(email: String, password: String): Customer {
        return Customer(
            customerEmail = email,
            customerPassword = password
        )
    }
    private fun insertCustomer(customer: Customer) {
        viewModelScope.launch {
            repository.insertCustomer(customer)
        }
    }

    fun retrieveCustomer(email: String, password: String): LiveData<Customer> {
        return repository.getCustomer(email, password).asLiveData()
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    fun isEntryValid(email: String, password: String, confirmPassword: String): Boolean {
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            return false
        }
        return true
    }
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 * Pass in the same constructor parameter as the RecipeViewModel that is the CustomerDao instance
 */
class CustomerViewModelFactory(private val customerDao: CustomerDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        /**
         * Check if the modelClass is the same as the RecipeViewModel class and return an instance of it.
         * Otherwise, throw an exception.
         *
         * Tips: The creation of the ViewModel factory is mostly boilerplate code, so you can reuse this code for future ViewModel factories.
         * */
        if (modelClass.isAssignableFrom(CustomerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CustomerViewModel(customerDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}