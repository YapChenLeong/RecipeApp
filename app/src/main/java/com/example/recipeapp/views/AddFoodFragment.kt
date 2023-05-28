package com.example.recipeapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.recipeapp.R
import com.example.recipeapp.RecipeApplication
import com.example.recipeapp.data.Item
import com.example.recipeapp.databinding.FragmentAddFoodBinding
import com.example.recipeapp.viewModels.RecipeViewModel
import com.example.recipeapp.viewModels.RecipeViewModelFactory

class AddFoodFragment : Fragment() {
    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: RecipeViewModel by activityViewModels {
        RecipeViewModelFactory(
            (activity?.application as RecipeApplication).database
                .itemDao()
        )
    }

    private val navigationArgs: DetailsFoodFragmentArgs by navArgs()

    lateinit var item: Item

    private var _binding: FragmentAddFoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddFoodBinding.inflate(inflater, container, false)

        val foodMenu = resources.getStringArray(R.array.food_menu)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, foodMenu)
        binding.itemFoods.setAdapter(arrayAdapter)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.itemId

        if (id > 0) {
            viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
                item = selectedItem
                bind(item)
            }
        } else {
            binding.saveAction.setOnClickListener {
                addNewItem()
            }
        }

    }

    private fun bind(item: Item) {
        val price = "%.2f".format(item.itemPrice)
        binding.apply {
            itemFoods.setText(item.itemType, TextView.BufferType.SPANNABLE)
            val itemType = resources.getStringArray(R.array.food_menu)
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, itemType)
            binding.itemFoods.setAdapter(arrayAdapter)

            itemName.setText(item.itemName, TextView.BufferType.SPANNABLE)
            itemPrice.setText(price, TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateItem() }
        }
    }

    private fun updateItem() {
        if (isEntryValid()) {
            viewModel.updateItem(
                this.navigationArgs.itemId,
                this.binding.itemFoods.text.toString(),
                this.binding.itemName.text.toString(),
                this.binding.itemPrice.text.toString()
            )
            val action = AddFoodFragmentDirections.actionAddFoodFragmentToFoodListFragment()
            findNavController().navigate(action)
        }
    }

    /**
     * Inserts the new Item into database and navigates up to list fragment.
     */
    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewItem(
                binding.itemFoods.text.toString(),
                binding.itemName.text.toString(),
                binding.itemPrice.text.toString()
            )

            //go back to main page
            val action = AddFoodFragmentDirections.actionAddFoodFragmentToFoodListFragment()
            findNavController().navigate(action)
        }
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemPrice.text.toString()
        )
    }


}