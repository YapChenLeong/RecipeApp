package com.example.recipeapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.RecipeAccountFragmentDirections
import com.example.recipeapp.RecipeApplication
import com.example.recipeapp.databinding.FragmentLoginBinding
import com.example.recipeapp.databinding.FragmentRecipeAccountBinding
import com.example.recipeapp.session.SessionManager
import com.example.recipeapp.viewModels.CustomerViewModel
import com.example.recipeapp.viewModels.CustomerViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView


class LoginFragment : Fragment() {
    private lateinit var sessionManager: SessionManager

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility = View.GONE
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener{
            sessionManager = SessionManager(requireContext())
            navigateToFoodListFragment()
        }
    }

    private fun navigateToFoodListFragment() {
        viewModel.retrieveCustomer(binding.etEmail.text.toString(), binding.etPassword.text.toString()).observe(this.viewLifecycleOwner) { customer ->

           if(customer !== null){
               val toast = Toast.makeText(requireContext(), "Successful login", Toast.LENGTH_SHORT);
               toast.show();
               //go to login page

               val progressBar = ProgressBar(requireContext())
               progressBar.visibility = View.VISIBLE

               sessionManager.setLoggedIn(true)

               val action = LoginFragmentDirections.actionLoginFragment3ToFoodListFragment()
               findNavController().navigate(action)
               disableBackNavigation()

           }else{
               val toast = Toast.makeText(requireContext(), "Invalid account", Toast.LENGTH_SHORT);
               toast.show();
           }
        }
    }

    private fun disableBackNavigation() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do nothing to disable back navigation
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}