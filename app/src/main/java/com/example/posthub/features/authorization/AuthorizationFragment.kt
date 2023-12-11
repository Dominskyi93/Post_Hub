package com.example.posthub.features.authorization

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.posthub.R
import com.example.posthub.core.ui.fragments.BaseFragment
import com.example.posthub.data.AuthResult
import com.example.posthub.databinding.FragmentAuthorizationBinding
import dagger.hilt.android.AndroidEntryPoint

private const val PREFS_FILE = "PrefsFile"
private const val MAIL_KEY = "pref_name"
private const val PASS_KEY = "pref_pass"
private const val CHECK_KEY = "pref_check"
private const val EMPTY_STRING = ""


@AndroidEntryPoint
class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?) ->
    FragmentAuthorizationBinding = { inflater, container ->
        FragmentAuthorizationBinding.inflate(inflater, container, false)
    }
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var mailEditText: EditText
    private lateinit var passwordEditText: EditText
    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.let { binding ->
            viewModel.let { viewModel ->
                sharedPrefs = requireContext().getSharedPreferences(PREFS_FILE, MODE_PRIVATE)
                mailEditText = binding.mailEdittext
                passwordEditText = binding.passwordEdittext
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
                            showToast(it.e.message.toString())
                        }

                        is AuthResult.Success -> {
                            if (binding.remember.isChecked) {
                                val editor = sharedPrefs.edit()
                                editor.putString(MAIL_KEY, mailEditText.text.toString())
                                editor.putString(
                                    PASS_KEY,
                                    passwordEditText.text.toString()
                                )
                                editor.putBoolean(CHECK_KEY, true)
                                editor.apply()
                                showToast("settings_have_been_saved")
                            } else {
                                sharedPrefs.edit().clear().apply()
                            }
                            findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
                            mailEditText.setText(EMPTY_STRING)
                            passwordEditText.setText(EMPTY_STRING)
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
        val sharedPreferences = requireContext().getSharedPreferences(PREFS_FILE, MODE_PRIVATE)
        binding.let { binding ->
            sharedPreferences.getString(MAIL_KEY, EMPTY_STRING)?.let { getMailFromSharedPref ->
                mailEditText.setText(getMailFromSharedPref)
            }
            sharedPreferences.getString(PASS_KEY, EMPTY_STRING)?.let { getPassFromSharedPref ->
                passwordEditText.setText(getPassFromSharedPref)
            }
            sharedPreferences.getBoolean(CHECK_KEY, false).let { getCheckFromSharedPref ->
                binding.remember.isChecked = getCheckFromSharedPref
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_SHORT
        ).show()
    }
}
