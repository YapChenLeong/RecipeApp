package com.example.recipeapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "email")
    val customerEmail: String,
    @ColumnInfo(name = "password")
    val customerPassword: String
)