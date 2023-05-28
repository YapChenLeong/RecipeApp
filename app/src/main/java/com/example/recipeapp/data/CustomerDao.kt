package com.example.recipeapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface CustomerDao {

    @Query("SELECT * FROM customer")
    fun getCustomers(): Flow<List<Customer>>

    @Query("SELECT * from customer WHERE email = :email AND password= :password")
    fun getCustomer(email: String, password: String): Flow<Customer>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCustomer(customer: Customer)

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)
}