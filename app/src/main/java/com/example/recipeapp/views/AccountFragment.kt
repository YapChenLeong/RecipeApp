package com.example.recipeapp.views

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.RecipeApplication
import com.example.recipeapp.databinding.FragmentAccountBinding
import com.example.recipeapp.session.SessionManager
import com.example.recipeapp.viewModels.CustomerViewModel
import com.example.recipeapp.viewModels.CustomerViewModelFactory

class AccountFragment : Fragment() {

    private val viewModel: CustomerViewModel by activityViewModels {
        CustomerViewModelFactory(
            (activity?.application as RecipeApplication).database
                .customerDao()
        )
    }

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayShowCustomEnabled(false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.account)


        // Inflate the layout for this fragment
        _binding =  FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutButton.setOnClickListener {
            sessionManager = SessionManager(requireContext())
            navigateToLoginFragment()
        }
    }

    private fun navigateToLoginFragment() {
        binding.progressBar.visibility = View.VISIBLE

        Handler().postDelayed({
            // Clear the login status
            sessionManager.setLoggedIn(false)
            findNavController().navigate(R.id.action_accountFragment_to_loginFragment3)
        }, 2000)
    }

}