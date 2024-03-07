package com.example.shopkaro.user_actions_and_web_request_handling

sealed interface AddressScreenUserActions {
    data class OnPinCodeClick(val pinCode: String) : AddressScreenUserActions
    data class OnCityClick(val city: String) : AddressScreenUserActions
    data class OnStateClick(val state: String) : AddressScreenUserActions
    data class OnHouseNoClick(val houseNo: String) : AddressScreenUserActions
    data class OnRoadOrAreaClick(val roadOrArea: String) : AddressScreenUserActions
    data class OnTypeOfAddressClick(val typeOfAddress: String) : AddressScreenUserActions
}