package com.example.recipeapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.RecipeAccountFragmentDirections
import com.example.recipeapp.RecipeApplication
import com.example.recipeapp.databinding.FragmentLoginBinding
import com.example.recipeapp.databinding.FragmentRecipeAccountBinding
import com.example.recipeapp.viewModels.CustomerViewModel
import com.example.recipeapp.viewModels.CustomerViewModelFactory


class LoginFragment : Fragment() {
    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: CustomerViewModel by activityViewModels {
        CustomerViewModelFactory(
            (activity?.application as RecipeApplication).database
                .customerDao()
        )
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener{
            navigateToFoodListFragment()
        }
    }

    private fun navigateToFoodListFragment() {
        viewModel.retrieveCustomer(binding.etEmail.text.toString(), binding.etPassword.text.toString()).observe(this.viewLifecycleOwner) { customer ->

           if(customer !== null){
               val toast = Toast.makeText(requireContext(), "Successful login", Toast.LENGTH_SHORT);
               toast.show();
               //go to login page
               val action = LoginFragmentDirections.actionLoginFragment3ToFoodListFragment()
               findNavController().navigate(action)

           }else{
               val toast = Toast.makeText(requireContext(), "Invalid account", Toast.LENGTH_SHORT);
               toast.show();
           }

        }
    }

}