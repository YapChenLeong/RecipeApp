package com.example.recipeapp.views

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.session.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView


class SplashScreenFragment : Fragment() {
    private lateinit var sessionManager: SessionManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility = View.GONE

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        sessionManager = SessionManager(requireContext())

        Handler(Looper.getMainLooper()).postDelayed({
            if (sessionManager.isLoggedIn()) {
                findNavController().navigate(R.id.action_splashScreenFragment_to_foodListFragment)
            }else{
                findNavController().navigate(R.id.action_splashScreenFragment_to_recipeAccountFragment)

            }
        }, 1000)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

}