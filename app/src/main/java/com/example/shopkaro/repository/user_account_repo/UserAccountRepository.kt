package com.example.shopkaro.repository.user_account_repo

import com.google.firebase.auth.FirebaseUser

interface UserAccountRepository {
    suspend fun createAccount(
        userEmail: String,
        password: String,
        onSuccess: (FirebaseUser?) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun signOutUser()

    suspend fun signIn(
        userEmail: String,
        password: String,
        onSuccess: (FirebaseUser?) -> Unit,
        onFailure: () -> Unit
    )
}