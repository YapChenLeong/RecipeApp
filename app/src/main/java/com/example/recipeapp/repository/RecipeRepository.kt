package com.example.recipeapp.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.data.Item
import com.example.recipeapp.data.ItemDao
import com.example.recipeapp.viewModels.RecipeViewModel
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val itemDao: ItemDao) {

    suspend fun insertItem(item: Item){
        itemDao.insert(item)
    }

    suspend fun updateItem(item: Item){
        itemDao.update(item)
    }

    suspend fun deleteItem(item: Item){
        itemDao.delete(item)
    }

    fun getAllItems(): Flow<List<Item>> {
        return itemDao.getItems()
    }
    fun getSingleItem(id: Int): Flow<Item> {
        return itemDao.getItem(id)
    }
}
