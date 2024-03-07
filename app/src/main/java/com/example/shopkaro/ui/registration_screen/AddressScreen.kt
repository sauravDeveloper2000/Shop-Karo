package com.example.shopkaro.ui.registration_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.shopkaro.R
import com.example.shopkaro.components.HorizontalSpace
import com.example.shopkaro.user_actions_and_web_request_handling.AddressScreenUserActions

private const val HOME = "HOME"
private const val OFFICE = "OFFICE"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(
    modifier: Modifier,
    backToPersonalInfoScreen: () -> Unit,
    personalInfoScreenViewModel: PersonalInfoScreenViewModel
) {
    var isHomeOrOfficeSelected by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val regex2 = Regex("^[1-9][0-9]{5}$")
    val regex3 = Regex("^[A-Za-z][A-Za-z\\s]{2,29}$")

    var pinCodeError by remember {
        mutableStateOf(false)
    }
    var pinCodeErrorCause by remember {
        mutableStateOf<String?>(null)
    }

    var cityError by remember {
        mutableStateOf(false)
    }
    var cityErrorCause by remember {
        mutableStateOf<String?>(null)
    }

    var stateError by remember {
        mutableStateOf(false)
    }
    var stateErrorCause by remember {
        mutableStateOf<String?>(null)
    }

    var houseNumberError by remember {
        mutableStateOf(false)
    }
    var houseNumberErrorCause by remember {
        mutableStateOf<String?>(null)
    }

    var roadOrAreaError by remember {
        mutableStateOf(false)
    }
    var roadOrAreaErrorCause by remember {
        mutableStateOf<String?>(null)
    }



    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.address),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            /**
             * elevated card for PinCode, city, state, house no. and road or area
             */
            ElevatedCard(
                modifier = Modifier.padding(10.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    text = stringResource(id = R.string.address_details),
                    textAlign = TextAlign.Center
                )

                /**
                 * For PinCode
                 */
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    value = personalInfoScreenViewModel.pinCode.toString(),
                    onValueChange = { value ->
                        personalInfoScreenViewModel.updateStates(
                            AddressScreenUserActions.OnPinCodeClick(
                                pinCode = value
                            )
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.pin_code),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    isError = pinCodeError,
                    supportingText = {
                        pinCodeErrorCause?.let {
                            Text(text = it)
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyMedium
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    /**
                     * For City
                     */

                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = personalInfoScreenViewModel.city,
                        onValueChange = { value ->
                            personalInfoScreenViewModel.updateStates(
                                AddressScreenUserActions.OnCityClick(
                                    value
                                )
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.city),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        isError = cityError,
                        supportingText = {
                            cityErrorCause?.let {
                                Text(text = it)
                            }
                        },
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                    HorizontalSpace(space = 10)
                    /**
                     * For State
                     */
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = personalInfoScreenViewModel.state,
                        onValueChange = { value ->
                            personalInfoScreenViewModel.updateStates(
                                AddressScreenUserActions.OnStateClick(
                                    state = value
                                )
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.state),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        isError = stateError,
                        supportingText = {
                            stateErrorCause?.let {
                                Text(text = it)
                            }
                        },
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                }

                /**
                 * For HouseNo.
                 */

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    value = personalInfoScreenViewModel.houseNumber,
                    onValueChange = { value ->
                        personalInfoScreenViewModel.updateStates(
                            AddressScreenUserActions.OnHouseNoClick(
                                houseNo = value
                            )
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.house_number),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    isError = houseNumberError,
                    supportingText = {
                        houseNumberErrorCause?.let {
                            Text(text = it)
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                /**
                 * Road or Area
                 */
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    value = personalInfoScreenViewModel.roadOrArea,
                    onValueChange = { value ->
                        personalInfoScreenViewModel.updateStates(
                            AddressScreenUserActions.OnRoadOrAreaClick(
                                roadOrArea = value
                            )
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.road_or_area),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    maxLines = 1,
                    singleLine = true,
                    isError = roadOrAreaError,
                    supportingText = {
                        roadOrAreaErrorCause?.let {
                            Text(text = it)
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(id = R.string.address_text),
                    textAlign = TextAlign.Center
                )
                /**
                 * Type of Address
                 */
                Row {
                    // Filter Chip for Home
                    FilterChip(
                        onClick = {
                            personalInfoScreenViewModel.updateStates(
                                AddressScreenUserActions.OnTypeOfAddressClick(
                                    typeOfAddress = HOME
                                )
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.house),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        selected = personalInfoScreenViewModel.typeOfAddress.contains(HOME),
                        leadingIcon = {
                            if (isHomeOrOfficeSelected.contains(HOME)) {
                                Icon(
                                    modifier = Modifier.size(AssistChipDefaults.IconSize),
                                    imageVector = Icons.Filled.Home,
                                    contentDescription = "Home"
                                )
                            } else {
                                Icon(
                                    modifier = Modifier.size(AssistChipDefaults.IconSize),
                                    imageVector = Icons.Outlined.Home,
                                    contentDescription = "Home"
                                )
                            }
                        },
                        shape = RoundedCornerShape(5.dp)
                    )
                    HorizontalSpace(space = 20)
                    // Filter Chip for Office
                    FilterChip(
                        onClick = {
                            personalInfoScreenViewModel.updateStates(
                                AddressScreenUserActions.OnTypeOfAddressClick(
                                    typeOfAddress = OFFICE
                                )
                            )
                        },
                        label = {
                            Text(
                                text = "Work",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        selected = personalInfoScreenViewModel.typeOfAddress.contains(OFFICE),
                        leadingIcon = {
                            if (isHomeOrOfficeSelected.contains(OFFICE)) {
                                Icon(
                                    modifier = Modifier.size(AssistChipDefaults.IconSize),
                                    imageVector = Icons.Filled.Apartment,
                                    contentDescription = "Apartment"
                                )
                            } else {
                                Icon(
                                    modifier = Modifier.size(AssistChipDefaults.IconSize),
                                    imageVector = Icons.Outlined.Apartment,
                                    contentDescription = "Apartment"
                                )
                            }
                        },
                        shape = RoundedCornerShape(5.dp)
                    )
                }
            }
            /**
             * Create new address or back to personal info screen
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        backToPersonalInfoScreen()
                    },
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = "Back")
                }
                /**
                 * Creates user account on firebase server
                 */
                Button(
                    onClick = {
                        /**
                         * Validation of PinCode field
                         */
                        if (personalInfoScreenViewModel.pinCode.isNotEmpty()) {
                            if (personalInfoScreenViewModel.pinCode.length == 6) {
                                if (personalInfoScreenViewModel.pinCode.matches(regex2)) {
                                    pinCodeError = false
                                    pinCodeErrorCause = null
                                } else {
                                    pinCodeError = true
                                    pinCodeErrorCause = "PinCode consists only digits."
                                }
                            } else {
                                pinCodeError = true
                                pinCodeErrorCause = "PinCode length must be 6 digit"
                            }
                        } else {
                            pinCodeError = true
                            pinCodeErrorCause = "Please enter your pinCode"
                        }
                        /**
                         * Validation for city field
                         */
                        if (personalInfoScreenViewModel.city.isNotEmpty()) {
                            if (personalInfoScreenViewModel.city.matches(regex3)) {
                                cityError = false
                                cityErrorCause = null
                            } else {
                                cityError = true
                                cityErrorCause =
                                    "City name should consist only alphabets \n and length in between 4-30 chars."
                            }
                        } else {
                            cityError = true
                            cityErrorCause = "Enter your city"
                        }
                        /**
                         * Validation for State field
                         */
                        if (personalInfoScreenViewModel.state.isNotEmpty()) {
                            if (personalInfoScreenViewModel.state.matches(regex3)) {
                                stateError = false
                                stateErrorCause = null
                            } else {
                                stateError = true
                                stateErrorCause =
                                    "State name should consist only alphabets \n and length in between 4-30 chars."
                            }
                        } else {
                            stateError = true
                            stateErrorCause = "Enter your State"
                        }
                        /**
                         * Validation of HouseNumber field
                         */
                        if (personalInfoScreenViewModel.houseNumber.isNotEmpty()) {
                            houseNumberError = false
                            houseNumberErrorCause = null
                        } else {
                            houseNumberError = true
                            houseNumberErrorCause = "Enter your house number."
                        }

                        /**
                         * Validation of Road or Area field
                         */
                        if (personalInfoScreenViewModel.roadOrArea.isNotEmpty()) {
                            roadOrAreaError = false
                            roadOrAreaErrorCause = null
                        } else {
                            roadOrAreaError = true
                            roadOrAreaErrorCause = "Enter your  road or area."
                        }

                        /**
                         * Final Validation of type of address
                         */
                        if (personalInfoScreenViewModel.typeOfAddress.isNullOrBlank()) {
                            Toast.makeText(
                                context,
                                "Please select your address type",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }

                        if (pinCodeError || cityError || stateError || houseNumberError || roadOrAreaError) {
                            Toast.makeText(context, "Validation Fail", Toast.LENGTH_SHORT).show()
                            return@Button
                        } else {
                            Toast.makeText(context, "Validation Succeeded", Toast.LENGTH_SHORT)
                                .show()
                            personalInfoScreenViewModel.createUserAccountWithUserCredentials(
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Account created successfuly",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onFailureOccurred = { cause ->
                                    Toast.makeText(context, cause, Toast.LENGTH_LONG).show()
                                }
                            )
                        }
                    },
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = stringResource(id = R.string.create))
                }
            }
        }
    }
}

