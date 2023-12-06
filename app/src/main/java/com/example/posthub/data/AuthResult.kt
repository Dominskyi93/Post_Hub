package com.example.posthub.data

import com.example.posthub.domain.User

sealed class AuthResult {

    data class Success(val user: User) : AuthResult()

    data class Error(val e: Exception) : AuthResult()

    object Loading : AuthResult()
}