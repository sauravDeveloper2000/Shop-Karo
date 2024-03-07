package com.example.shopkaro.ui.account_screen.address_section

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopkaro.model_class.Address
import com.example.shopkaro.repository.address_repo.AddressRepo
import com.example.shopkaro.user_actions_and_web_request_handling.AddressScreenUserActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewAddressScreenViewModel @Inject constructor(
    private val addressRepo: AddressRepo
) : ViewModel() {

    var name by mutableStateOf("")
        private set
    var pinCode by mutableStateOf("")
        private set
    var city by mutableStateOf("")
        private set
    var state by mutableStateOf("")
        private set
    var houseNumber by mutableStateOf("")
        private set
    var roadOrArea by mutableStateOf("")
        private set
    var typeOfAddress by mutableStateOf(" ")
        private set

    init {
        addressRepo.readsDataFromCollection()
    }

    /**
     * updateStates() - This function updates the mention above states using UDF principle
     */
    fun updateStates(
        userAction: AddressScreenUserActions
    ) {
        when (userAction) {
            is AddressScreenUserActions.OnCityClick -> {
                city = userAction.city
            }

            is AddressScreenUserActions.OnHouseNoClick -> {
                houseNumber = userAction.houseNo
            }

            is AddressScreenUserActions.OnPinCodeClick -> {
                pinCode = userAction.pinCode
            }

            is AddressScreenUserActions.OnRoadOrAreaClick -> {
                roadOrArea = userAction.roadOrArea
            }

            is AddressScreenUserActions.OnStateClick -> {
                state = userAction.state
            }

            is AddressScreenUserActions.OnTypeOfAddressClick -> {
                typeOfAddress = userAction.typeOfAddress
            }
        }
    }

    fun updateNameState(
        userName: String
    ) {
        name = userName
    }

    fun addsNewAddress(
        inSuccessState: () -> Unit,
        inFailureState: (String) -> Unit
    ) {
        viewModelScope.launch {
            val address = Address(
                userName = name,
                pinCode = pinCode,
                city = city,
                state = state,
                houseNumber = houseNumber,
                roadOrArea = roadOrArea,
                typeOfAddress = typeOfAddress
            )
            addressRepo.addsNewAddress(
                address = address,
                success = {
                    inSuccessState()
                },
                onFailure = { cause ->
                    inFailureState(cause)
                }
            )
        }
    }
}
