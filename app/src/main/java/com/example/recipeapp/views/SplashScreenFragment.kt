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


class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        Handler(Looper.getMainLooper()).postDelayed({
//            findNavController().navigate(R.id.action_splashScreenFragment_to_foodListFragment)
            findNavController().navigate(R.id.action_splashScreenFragment_to_recipeAccountFragment)

        }, 1000)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

}