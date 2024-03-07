package com.example.shopkaro.user_actions_and_web_request_handling

/**
 * LoginScreenViewModel - It handles user actions happening on login screen
 */
sealed interface LoginScreenUserActions {
    data class OnEmailFieldClick(val userEmail: String) : LoginScreenUserActions
    data class OnPasswordFieldClick(val password: String) : LoginScreenUserActions
}