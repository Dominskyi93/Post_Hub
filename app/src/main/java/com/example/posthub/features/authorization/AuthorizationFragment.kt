package com.example.posthub.features.authorization

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.posthub.R
import com.example.posthub.core.ui.fragments.BaseFragment
import com.example.posthub.data.AuthResult
import com.example.posthub.databinding.FragmentAuthorizationBinding
import dagger.hilt.android.AndroidEntryPoint

private const val PREFS_NAME = "PrefsFile"

@AndroidEntryPoint
class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>() {
    private lateinit var sharedPrefs: SharedPreferences

    override val bindingInflater: (LayoutInflater, ViewGroup?) -> FragmentAuthorizationBinding =
        { inflater, container ->
            FragmentAuthorizationBinding.inflate(inflater, container, false)
        }

    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.let { binding ->
            viewModel.let { viewModel ->
                sharedPrefs = requireContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

                getPreferences()

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
                            if (binding.remember.isChecked) {
                                val editor = sharedPrefs.edit()
                                editor.putString("pref_name", binding.mailEdittext.text.toString())
                                editor.putString("pref_pass", binding.passwordEdittext.text.toString())
                                editor.putBoolean("pref_check", true)
                                editor.apply()
                                Toast.makeText(
                                    requireContext().applicationContext,
                                    "Settings have been saved!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                sharedPrefs.edit().clear().apply()
                            }

                            findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
                            binding.mailEdittext.setText("")
                            binding.passwordEdittext.setText("")
                        }
                    }
                }

                binding.signIn.setOnClickListener {
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
        }
    }

    private fun getPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        binding.let { binding ->
            sharedPreferences.getString("pref_name", "")?.let { getMailFromSharedPref ->
                binding.mailEdittext.setText(getMailFromSharedPref)
            }
            sharedPreferences.getString("pref_pass", "")?.let { getPassFromSharedPref ->
                binding.passwordEdittext.setText(getPassFromSharedPref)
            }
            sharedPreferences.getBoolean("pref_check", false).let { getCheckFromSharedPref ->
                binding.remember.isChecked = getCheckFromSharedPref
            }
        }
    }
}
