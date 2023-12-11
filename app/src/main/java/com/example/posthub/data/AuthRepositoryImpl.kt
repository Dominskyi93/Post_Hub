package com.example.posthub.data

import com.example.posthub.domain.AuthRepository
import com.example.posthub.domain.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {

    override suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult {
        return try {
            val user = auth.signInWithEmailAndPassword(email, password).await().user!!
            AuthResult.Success(User.Base(user.email ?: " ", user.uid))
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthResult {
        return try {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user!!
            AuthResult.Success(User.Base(user.email ?: " ", user.uid))
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override fun signOut() {
        auth.signOut()
    }
}