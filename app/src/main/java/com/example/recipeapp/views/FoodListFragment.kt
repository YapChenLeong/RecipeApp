package com.example.recipeapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.RecipeApplication
import com.example.recipeapp.adapters.FoodItemListAdapter
import com.example.recipeapp.databinding.FragmentFoodListBinding
import com.example.recipeapp.viewModels.RecipeViewModel
import com.example.recipeapp.viewModels.RecipeViewModelFactory

class FoodListFragment : Fragment() {
    private val viewModel: RecipeViewModel by activityViewModels {
        RecipeViewModelFactory(
            (activity?.application as RecipeApplication).database.itemDao()
        )
    }

    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodListBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayShowCustomEnabled(false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.app_name)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FoodItemListAdapter {
            val action = FoodListFragmentDirections.actionFoodListFragmentToDetailsFoodFragment(it.id)//nav graph
            this.findNavController().navigate(action)
        }
//        binding.gridRecyclerView.layoutManager = LinearLayoutManager(this.context)
//        binding.gridRecyclerView.adapter = FoodItemListAdapter(this.context, 1)
        // Specify fixed size to improve performance
        binding.gridRecyclerView.adapter = adapter
        binding.gridRecyclerView.setHasFixedSize(true)

        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->

            if (items.isNotEmpty()) {

                (binding.gridRecyclerView.adapter as FoodItemListAdapter).list = items
                (binding.gridRecyclerView.adapter as FoodItemListAdapter).notifyDataSetChanged()
            }

        }

        binding.floatingActionButton.setOnClickListener {
            val action = FoodListFragmentDirections.actionFoodListFragmentToAddFoodFragment(
                getString(R.string.add_fragment_title)
            )
//            val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment(
//                getString(R.string.add_fragment_title)
//            )
            this.findNavController().navigate(action)
        }
    }

}