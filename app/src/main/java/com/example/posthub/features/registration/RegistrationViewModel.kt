package com.example.posthub.features.registration

import com.example.posthub.core.ui.BaseViewModel
import com.example.posthub.data.AuthResult
import com.example.posthub.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {

    override val sendRequest: suspend (String, String) -> AuthResult =
        { email, password -> authRepository.signUpWithEmailAndPassword(email, password) }

}