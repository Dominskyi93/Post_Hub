package com.example.posthub.domain

import com.example.posthub.data.AuthResult

interface AuthRepository {

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult

    suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthResult

    fun signOut()
    //FirebaseAuth.getInstance().currentUser
}
