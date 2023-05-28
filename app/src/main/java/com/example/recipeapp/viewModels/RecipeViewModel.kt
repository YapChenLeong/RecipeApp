package com.example.recipeapp.viewModels

import androidx.lifecycle.*
import com.example.recipeapp.data.Item
import com.example.recipeapp.data.ItemDao
import com.example.recipeapp.repository.RecipeRepository
import kotlinx.coroutines.launch
import java.util.*

class RecipeViewModel(private val itemDao: ItemDao) : ViewModel() {


    private lateinit var repository: RecipeRepository

    init {
        viewModelScope.launch {
            repository = RecipeRepository(itemDao)
        }
    }

    var allItems: LiveData<List<Item>> = repository.getAllItems().asLiveData()

    fun retrieveItem(id: Int): LiveData<Item> {
        return repository.getSingleItem(id).asLiveData()
//        return itemDao.getItem(id).asLiveData() //without repo use this code
    }

    /**#################################### START ADD DATA  *####################################*/
    fun addNewItem(itemType: String, itemName: String, itemPrice: String) {
        val newItem = getNewItemEntry(itemType, itemName, itemPrice)
        insertItem(newItem)
    }

    private fun getNewItemEntry(itemType: String, itemName: String, itemPrice: String): Item {
        return Item(
            itemType = itemType,
            itemName = itemName,
            itemPrice = itemPrice.toDouble()
        )
    }

    private fun insertItem(item: Item) {
        viewModelScope.launch {
            repository.insertItem(item)
        }
    }
    /**#################################### END ADD DATA  *####################################*/

    /**#################################### UPDATE DATA  *####################################*/
    /**
     * Updates an existing Item in the database.
     */
    fun updateItem(
        itemId: Int,
        itemType: String,
        itemName: String,
        itemPrice: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemType, itemName, itemPrice)
        updateItem(updatedItem)
    }

    /**
     * Called to update an existing entry in the Inventory database.
     * Returns an instance of the [Item] entity class with the item info updated by the user.
     */
    private fun getUpdatedItemEntry(
        itemId: Int,
        itemType: String,
        itemName: String,
        itemPrice: String
    ): Item {
        return Item(
            id = itemId,
            itemType = itemType,
            itemName = itemName,
            itemPrice = itemPrice.toDouble()
        )
    }

    /**
     * Launching a new coroutine to update an item in a non-blocking way
     */
    private fun updateItem(item: Item) {
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }
    /**#################################### END UPDATE DATA  *####################################*/


    /**#################################### DELETE DATA  *####################################*/
    /**
     * Launching a new coroutine to delete an item in a non-blocking way
     */
    fun deleteItem(item: Item) {
        viewModelScope.launch {
            repository.deleteItem(item)
//            itemDao.delete(item) //without repo use this code
        }
    }
    /**#################################### END DELETE DATA  *####################################*/




    /**
     * Returns true if the EditTexts are not empty
     */
    fun isEntryValid(itemName: String, itemPrice: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank()) {
            return false
        }
        return true
    }
}

    /**
    * Factory class to instantiate the [ViewModel] instance.
    * Pass in the same constructor parameter as the RecipeViewModel that is the ItemDao instance
    */
    class RecipeViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
        /**
         * Check if the modelClass is the same as the RecipeViewModel class and return an instance of it.
         * Otherwise, throw an exception.
         *
         * Tips: The creation of the ViewModel factory is mostly boilerplate code, so you can reuse this code for future ViewModel factories.
         * */
            if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RecipeViewModel(itemDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }