package com.example.shopkaro.user_actions_and_web_request_handling

/**
 * It handles or defines possible user actions.
 */

sealed interface PersonalInfoScreenUserActions {
    data class OnNameClick(val name: String) : PersonalInfoScreenUserActions
    data class OnEmailId(val emailId: String) : PersonalInfoScreenUserActions
    data class OnNewPasswordClick(val newPassword: String) : PersonalInfoScreenUserActions
    data class OnConfirmPasswordClick(val confirmPassword: String) : PersonalInfoScreenUserActions
}