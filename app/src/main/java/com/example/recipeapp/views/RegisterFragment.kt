package com.example.recipeapp.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.RecipeApplication
import com.example.recipeapp.databinding.FragmentRegisterBinding
import com.example.recipeapp.viewModels.CustomerViewModel
import com.example.recipeapp.viewModels.CustomerViewModelFactory
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable

@SuppressLint("CheckResult")
class RegisterFragment : Fragment() {
    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: CustomerViewModel by activityViewModels {
        CustomerViewModelFactory(
            (activity?.application as RecipeApplication).database
                .customerDao()
        )
    }

    private var _binding: FragmentRegisterBinding? = null
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
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Email Validation
        val emailStream = RxTextView.textChanges(binding.etRegisterEmail)
            .skipInitialValue()
            .map{ email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe{
            showEmailValidAlert(it)
        }

        //Password Validation
        val passwordStream = RxTextView.textChanges(binding.etRegisterPassword)
            .skipInitialValue()
            .map{ password ->
                password.length < 8
            }
        passwordStream.subscribe{
            showTextMinimalAlert(it, "Password")
        }

        //Confirm Password Validation
        val passwordConfirmStream = Observable.merge(
        RxTextView.textChanges(binding.etRegisterPassword)
            .skipInitialValue()
            .map{ password ->
                password.toString() != binding.etConfirmPassword.text.toString()
            },
        RxTextView.textChanges(binding.etConfirmPassword)
            .skipInitialValue()
            .map{ confirmPassword ->
                confirmPassword.toString() != binding.etRegisterPassword.text.toString()
            })
        passwordConfirmStream.subscribe{
            showPasswordConfirmAlert(it)
        }

        binding.btnRegister.setOnClickListener{
            addNewCustomer()
        }
    }

    private fun showNameExistAlert(isNotValid: Boolean){
        binding.etRegisterEmail.error = if (isNotValid) "Cannot empty!" else null
    }

    private fun showTextMinimalAlert(isNotValid: Boolean, text: String){
        if(text == "Email"){
            binding.etRegisterEmail.error = if (isNotValid) "More than 6 characters!" else null
        }else if(text == "Password"){
            binding.etRegisterPassword.error = if (isNotValid) "More than 8 characters!" else null
        }
    }

    private fun showEmailValidAlert(isNotValid: Boolean){
        binding.etRegisterEmail.error = if (isNotValid) "Email not valid!" else null
    }

    private fun showPasswordConfirmAlert(isNotValid: Boolean){
        binding.etConfirmPassword.error = if (isNotValid) "Password not same!" else null
    }


    private fun addNewCustomer() {
        if (isEntryValid()) {
            viewModel.addNewCustomer(
                binding.etRegisterEmail.text.toString(),
                binding.etRegisterPassword.text.toString()
            )

            val toast = Toast.makeText(requireContext(), "Customer Created", Toast.LENGTH_SHORT);
            toast.show();

            //go to login page
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment3()
            findNavController().navigate(action)

        }else{
            val toast = Toast.makeText(requireContext(), "Complete all the fields", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.etRegisterEmail.text.toString(),
            binding.etRegisterPassword.text.toString(),
            binding.etConfirmPassword.text.toString()
        )
    }

}