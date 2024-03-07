package com.example.shopkaro.ui.login_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopkaro.components.debugLog
import com.example.shopkaro.repository.user_account_repo.UserAccountRepository
import com.example.shopkaro.user_actions_and_web_request_handling.LoginScreenUserActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * LoginScreenViewModel - Contains logic of login screen view model
 */
@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val userAccountRepository: UserAccountRepository
) : ViewModel() {
    var userEmail by mutableStateOf<String>("")
        private set
    var password by mutableStateOf<String>("")
        private set

    /**
     * onUserAction function give response to user upon that particular action.
     */
    fun onUserAction(
        userAction: LoginScreenUserActions
    ) {
        when (userAction) {
            is LoginScreenUserActions.OnEmailFieldClick -> {
                userEmail = userAction.userEmail
            }

            is LoginScreenUserActions.OnPasswordFieldClick -> {
                password = userAction.password
            }
        }
    }

    fun logIn(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            userAccountRepository.signIn(
                userEmail = userEmail,
                password = password,
                onSuccess = { fireBaseUser ->
                    fireBaseUser?.let {
                        debugLog(msg = "${it.uid}")
                        debugLog(msg = "${it.email}")
                    }
                    onSuccess()
                },
                onFailure = {
                    onFailure()
                }
            )
        }
    }

    fun resetStates() {
        userEmail = ""
        password = ""
    }
}