package com.example.posthub.features.authorization

import com.example.posthub.core.ui.viewModels.BaseViewModel
import com.example.posthub.data.AuthResult
import com.example.posthub.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {

    override val sendRequest: suspend (String, String) -> AuthResult =
        { email, password -> authRepository.signInWithEmailAndPassword(email, password) }

}