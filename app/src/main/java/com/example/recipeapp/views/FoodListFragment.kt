package com.example.recipeapp.views

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.RecipeApplication
import com.example.recipeapp.adapters.FoodItemListAdapter
import com.example.recipeapp.databinding.FragmentFoodListBinding
import com.example.recipeapp.viewModels.RecipeViewModel
import com.example.recipeapp.viewModels.RecipeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class FoodListFragment : Fragment() {
//    private val navigationArgs: FoodListFragmentArgs by navArgs()

    private val viewModel: RecipeViewModel by activityViewModels {
        RecipeViewModelFactory(
            (activity?.application as RecipeApplication).database.itemDao()
        )
    }

    private var backPressedOnce = false
    private var isGridLayoutManager = true
    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility = View.VISIBLE
        _binding = FragmentFoodListBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayShowCustomEnabled(false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.app_name)

        // Enable options menu for the fragment
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val id = navigationArgs.customerId

        // sets up the adapter with a callback function to handle item clicks
        // when an item is clicked, it navigates to the DetailsFoodFragment passing the corresponding item ID as an argument.
        val adapter = FoodItemListAdapter {
            val action = FoodListFragmentDirections.actionFoodListFragmentToDetailsFoodFragment(it.id)//nav graph
            this.findNavController().navigate(action)
        }
//        binding.gridRecyclerView.layoutManager = LinearLayoutManager(this.context)
//        binding.gridRecyclerView.adapter = FoodItemListAdapter(this.context, 1)
        // Specify fixed size to improve performance
        chooseLayout()

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
        disableBackNavigation(view)
    }

    private fun chooseLayout() {
        if (isGridLayoutManager) {
            binding.gridRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        } else {
            binding.gridRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return

        menuItem.icon =
            if (isGridLayoutManager)
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_grid_layout)
            else ContextCompat.getDrawable(requireContext(), R.drawable.ic_linear_layout)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val layoutButton = menu?.findItem(R.id.action_switch_layout)
        // Calls code to set the icon based on the LinearLayoutManager of the RecyclerView
        setIcon(layoutButton)
    }

    /**
     * Determines how to handle interactions with the selected [MenuItem]
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                // Sets isLinearLayoutManager (a Boolean) to the opposite value
                isGridLayoutManager = !isGridLayoutManager
                // Sets layout and icon
                chooseLayout()
                setIcon(item)

                return true
            }
            //  Otherwise, do nothing and use the core event handling

            // when clauses require that all possible paths be accounted for explicitly,
            //  for instance both the true and false cases if the value is a Boolean,
            //  or an else to catch all unhandled cases.
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun disableBackNavigation(view: View) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedOnce) {
                    requireActivity().finish()
                } else {
                    backPressedOnce = true
                    Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show()
                    view.postDelayed({ backPressedOnce = false }, 2000)
                }

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}