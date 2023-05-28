package com.example.recipeapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.recipeapp.R
import com.example.recipeapp.RecipeApplication
import com.example.recipeapp.data.Item
import com.example.recipeapp.data.getFormattedPrice
import com.example.recipeapp.databinding.FragmentDetailsFoodBinding
import com.example.recipeapp.viewModels.RecipeViewModel
import com.example.recipeapp.viewModels.RecipeViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DetailsFoodFragment : Fragment() {
    private val navigationArgs: DetailsFoodFragmentArgs by navArgs()
    lateinit var item: Item

    private val viewModel: RecipeViewModel by activityViewModels {
        RecipeViewModelFactory(
            (activity?.application as RecipeApplication).database.itemDao()
        )
    }

    private var _binding: FragmentDetailsFoodBinding ? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId
        // Retrieve the item details using the itemId.
        // Attach an observer on the data (instead of polling for changes) and only update the
        // the UI when the data actually changes.
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            item = selectedItem
            bind(item)
        }
    }


    /**
     * Binds views with the passed in item data.
     */
    private fun bind(item: Item){
        binding.apply {
            itemName.text = item.itemName
            itemPrice.text = item.getFormattedPrice()

            when(item.itemType) {
                "Chicken" -> {
                    binding.imageView.setImageResource(R.drawable.chicken)
                }
                "Steak" ->{
                    binding.imageView.setImageResource(R.drawable.steak)
                }
                "Lamb" ->{
                    binding.imageView.setImageResource(R.drawable.lamb)
                }
                "Pasta" ->{
                    binding.imageView.setImageResource(R.drawable.pasta)
                }
                "Seafood" ->{
                    binding.imageView.setImageResource(R.drawable.seafood)
                }
                "Dessert" ->{
                    binding.imageView.setImageResource(R.drawable.dessert)
                }
            }

//            sellItem.setOnClickListener { viewModel.sellItem(item) }
            deleteItem.setOnClickListener { showConfirmationDialog() }
            editItem.setOnClickListener { editItem() }
        }
    }

    /**
     * Navigate to the Edit item screen.
     */
    private fun editItem() {
        val action = DetailsFoodFragmentDirections.actionDetailsFoodFragmentToAddFoodFragment(
            getString(R.string.edit_fragment_title),
            item.id
        )
        this.findNavController().navigate(action)
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }

    /**
     * Deletes the current item and navigates to the list fragment.
     */
    private fun deleteItem() {
        viewModel.deleteItem(item)
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}