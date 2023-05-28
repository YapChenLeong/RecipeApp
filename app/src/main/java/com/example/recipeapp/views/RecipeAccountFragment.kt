package com.example.recipeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.databinding.FragmentRecipeAccountBinding
import com.example.recipeapp.views.AddFoodFragmentDirections


class RecipeAccountFragment : Fragment() {

    private var _binding: FragmentRecipeAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        // Inflate the layout for this fragment
        _binding = FragmentRecipeAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener{
            navigateLogin()
        }
        binding.btnRegister.setOnClickListener{
            navigateRegister()
        }
    }

    private fun navigateLogin() {
        //go to login page
        val action = RecipeAccountFragmentDirections.actionRecipeAccountFragmentToLoginFragment3()
        findNavController().navigate(action)
    }

    private fun navigateRegister() {
        //go to register page
        val action = RecipeAccountFragmentDirections.actionRecipeAccountFragmentToRegisterFragment()
        findNavController().navigate(action)
    }
}