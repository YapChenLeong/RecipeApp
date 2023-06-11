package com.example.recipeapp.session

import android.content.Context
import android.content.SharedPreferences

class SessionManager (private val context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("session_pref", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPref.edit()

    fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean("isLoggedIn", false)
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }
}