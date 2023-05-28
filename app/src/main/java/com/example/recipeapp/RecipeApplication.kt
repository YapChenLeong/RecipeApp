package com.example.recipeapp

import android.app.Application
import com.example.recipeapp.data.ItemRoomDatabase

class RecipeApplication : Application(){
     // Using by lazy so the database is only created when needed
     // rather than when the application starts
     val database: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }
 }