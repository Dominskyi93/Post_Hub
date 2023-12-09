package com.example.posthub.features.authorization

import android.content.Context
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.posthub.R
import com.example.posthub.core.ui.MainActivity
import com.example.posthub.core.ui.fragments.BaseFragment
import com.example.posthub.data.AuthResult
import com.example.posthub.databinding.FragmentAuthorizationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>() {
//    private lateinit var editor: Editor
//    private lateinit var authMail:
    override val bindingInflater: (LayoutInflater, ViewGroup?) -> FragmentAuthorizationBinding =
        { inflater, container ->
            FragmentAuthorizationBinding.inflate(inflater, container, false)
        }

    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inputList = listOf(
            binding.authMail,
            binding.authPassword
        )

        viewModel.authState.observe(viewLifecycleOwner) {
            when (it) {
                AuthResult.Loading -> binding.progressBar.visibility = View.VISIBLE
                is AuthResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.e.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }

                is AuthResult.Success -> {
                    findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
                }
            }
        }

        binding.signIn.setOnClickListener {
//            authMail = binding.mailEdittext
//            val authPassword = binding.passwordEdittext
//            val remember = binding.remember
//            val sharedPreferences =
//                requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
//            editor = sharedPreferences.edit()
//
//            if (sharedPreferences.getBoolean("remember", false)) {
//                remember.isChecked = true
//            } else {
//                remember.isChecked = false
//            }
//            authMail.setText(sharedPreferences.getString("username", ""))
//            authPassword.setText(sharedPreferences.getString("password", ""))
//
//            authMail.addTextChangedListener()
//            authPassword.addTextChangedListener()
//            remember.setOnCheckedChangeListener()


            val allValidation = inputList.map { it.isValid() }

            if (allValidation.all { it }) {
                viewModel.sendCredentials(
                    email = binding.authMail.text(),
                    password = binding.authPassword.text()
                )
            }
        }
        binding.navigateToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }
    }

//    private fun managePrefs() {
//        editor.putString("username", aut)
//    }
}
