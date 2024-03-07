package com.example.shopkaro.repository.user_account_repo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class UserAccountRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UserAccountRepository {

    override suspend fun createAccount(
        userEmail: String,
        password: String,
        onSuccess: (FirebaseUser?) -> Unit,
        onFailure: (String) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = task.result.user
                    onSuccess(firebaseUser)
                } else {
                    onFailure(
                        task.exception.toString()
                    )
                }
            }
    }

    override suspend fun signOutUser() {
        firebaseAuth.signOut()
    }

    override suspend fun signIn(
        userEmail: String,
        password: String,
        onSuccess: (FirebaseUser?) -> Unit,
        onFailure: () -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener { task ->
                try {
                    val firebaseUser = task.result.user
                    onSuccess(firebaseUser)
                } catch (e: Exception) {
                    onFailure()
                }
            }.addOnCanceledListener {
                onFailure()
            }
    }
}