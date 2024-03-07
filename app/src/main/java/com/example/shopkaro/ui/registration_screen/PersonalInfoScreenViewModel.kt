package com.example.shopkaro.ui.registration_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopkaro.components.debugLog
import com.example.shopkaro.model_class.Address
import com.example.shopkaro.repository.address_repo.AddressRepo
import com.example.shopkaro.repository.user_account_repo.UserAccountRepository
import com.example.shopkaro.user_actions_and_web_request_handling.AddressScreenUserActions
import com.example.shopkaro.user_actions_and_web_request_handling.PersonalInfoScreenUserActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This view model - PersonalInfoScreenViewModel handles personal info screen logic.
 */
@HiltViewModel
class PersonalInfoScreenViewModel @Inject constructor(
    private val userAccountRepository: UserAccountRepository,
    private val addressRepo: AddressRepo
) : ViewModel() {

    var name by mutableStateOf<String>("")
        private set
    var emailId by mutableStateOf<String>("")
        private set
    var newPassword by mutableStateOf<String>("")
        private set
    var confirmPassword by mutableStateOf<String>("")
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
    var typeOfAddress by mutableStateOf("")
        private set

    /**
     * updateState() - This function is used to update states of address screen by utilizing UDF principle
     */
    fun updateStates(
        addressScreenUserActions: AddressScreenUserActions
    ) {
        when (addressScreenUserActions) {
            is AddressScreenUserActions.OnCityClick -> {
                city = addressScreenUserActions.city
            }

            is AddressScreenUserActions.OnHouseNoClick -> {
                houseNumber = addressScreenUserActions.houseNo
            }

            is AddressScreenUserActions.OnPinCodeClick -> {
                pinCode = addressScreenUserActions.pinCode
            }

            is AddressScreenUserActions.OnRoadOrAreaClick -> {
                roadOrArea = addressScreenUserActions.roadOrArea
            }

            is AddressScreenUserActions.OnStateClick -> {
                state = addressScreenUserActions.state
            }

            is AddressScreenUserActions.OnTypeOfAddressClick -> {
                typeOfAddress = addressScreenUserActions.typeOfAddress
            }
        }
    }

    /**
     *  setsState - It initializes the above mention five states on bases of user action.
     *  And this takes an user action. And according to user action it initializes the state.
     */
    fun setsState(
        personalInfoScreenUserActions: PersonalInfoScreenUserActions
    ) {
        when (personalInfoScreenUserActions) {
            is PersonalInfoScreenUserActions.OnNameClick -> {
                name = personalInfoScreenUserActions.name
            }

            is PersonalInfoScreenUserActions.OnEmailId -> {
                emailId = personalInfoScreenUserActions.emailId
            }

            is PersonalInfoScreenUserActions.OnNewPasswordClick -> {
                newPassword = personalInfoScreenUserActions.newPassword
            }

            is PersonalInfoScreenUserActions.OnConfirmPasswordClick -> {
                confirmPassword = personalInfoScreenUserActions.confirmPassword
            }
        }
    }

    /**
     * createAccountWithEmailAndPassword - This functions create user account on firebase server.
     */
    fun createUserAccountWithUserCredentials(
        onSuccess: () -> Unit,
        onFailureOccurred: (String) -> Unit
    ) {
        viewModelScope.launch {
            val address = Address(
                userName = name,
                pinCode = pinCode,
                typeOfAddress = typeOfAddress,
                city = city,
                houseNumber = houseNumber,
                roadOrArea = roadOrArea,
                state = state
            )
            userAccountRepository.createAccount(
                userEmail = emailId,
                password = newPassword,
                onSuccess = { firebaseUser ->
                    firebaseUser?.let {
                        debugLog(msg = it.uid)
                        it.email?.let { it1 -> debugLog(msg = it1) }
                        it.displayName?.let { it1 -> debugLog(msg = it1) }
                    }
                    onSuccess() //Toast.makeText(context, "Account created successfully", Toast.LENGTH_SHORT).show())
                    viewModelScope.launch {
                        addressRepo.addsNewAddress(
                            success = {},
                            onFailure = {
                                debugLog(msg = it)
                            },
                            address = address
                        )
                    }
                },
                onFailure = {
                    onFailureOccurred(it)
                }
            )
        }
    }
}