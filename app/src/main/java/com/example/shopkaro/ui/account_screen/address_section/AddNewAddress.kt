package com.example.shopkaro.ui.account_screen.address_section

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopkaro.components.HorizontalSpace
import com.example.shopkaro.user_actions_and_web_request_handling.AddressScreenUserActions

private const val HOME = "Home"
private const val OFFICE = "Office"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewAddress(
    modifier: Modifier = Modifier,
    backToAccountSection: () -> Unit,
    addNewAddressScreenViewModel: AddNewAddressScreenViewModel = hiltViewModel()
) {

    var nameError by remember {
        mutableStateOf(false)
    }
    var nameErrorCause by remember {
        mutableStateOf<String?>(null)
    }

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

    var context = LocalContext.current

    Scaffold(
        modifier = modifier.padding(10.dp),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    val regex = Regex("^[A-Za-z][A-Z a-z]{2,49}")
                    val regex2 = Regex("^[1-9][0-9]{5}$")
                    val regex3 = Regex("^[A-Za-z][A-Za-z\\s]{2,29}$")

                    /**
                     * Upon clicking on this button, this should do validation.
                     *  1st name field validation
                     */
                    if (addNewAddressScreenViewModel.name.isNotEmpty()) {
                        if (addNewAddressScreenViewModel.name.length in 2..49) {
                            if (addNewAddressScreenViewModel.name.matches(regex = regex)) {
                                nameError = false
                                nameErrorCause = null
                            } else {
                                nameError = true
                                nameErrorCause = "name should only consists alphabets"
                            }
                        } else {
                            nameError = true
                            nameErrorCause = "Length should be in between 3 to 50"
                        }
                    } else {
                        nameError = true
                        nameErrorCause = "Please entre your name!"
                    }
                    /**
                     * Validation of PinCode field
                     */
                    if (addNewAddressScreenViewModel.pinCode.isNotEmpty()) {
                        if (addNewAddressScreenViewModel.pinCode.length == 6) {
                            if (addNewAddressScreenViewModel.pinCode.matches(regex2)) {
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
                    if (addNewAddressScreenViewModel.city.isNotEmpty()) {
                        if (addNewAddressScreenViewModel.city.matches(regex3)) {
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
                    if (addNewAddressScreenViewModel.state.isNotEmpty()) {
                        if (addNewAddressScreenViewModel.state.matches(regex3)) {
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
                    if (addNewAddressScreenViewModel.houseNumber.isNotEmpty()) {
                        houseNumberError = false
                        houseNumberErrorCause = null
                    } else {
                        houseNumberError = true
                        houseNumberErrorCause = "Enter your house number."
                    }

                    /**
                     * Validation of Road or Area field
                     */
                    if (addNewAddressScreenViewModel.roadOrArea.isNotEmpty()) {
                        roadOrAreaError = false
                        roadOrAreaErrorCause = null
                    } else {
                        roadOrAreaError = true
                        roadOrAreaErrorCause = "Enter your  road or area."
                    }

                    /**
                     * Final Validation of type of address
                     */
                    if (addNewAddressScreenViewModel.typeOfAddress.isNullOrBlank()) {
                        Toast.makeText(
                            context,
                            "Please select your address type",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@ExtendedFloatingActionButton
                    }

                    if (nameError || pinCodeError || cityError || stateError || houseNumberError || roadOrAreaError) {
                        Toast.makeText(context, "Validation Fail", Toast.LENGTH_SHORT).show()
                        return@ExtendedFloatingActionButton
                    } else {
                        Toast.makeText(context, "Validation Succeeded", Toast.LENGTH_SHORT).show()
                        addNewAddressScreenViewModel.addsNewAddress(
                            inSuccessState = {
                                backToAccountSection()

                            },
                            inFailureState = {
                                Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }

                }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add new address")
                HorizontalSpace(space = 5)
                Text(
                    text = "Add",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    ) { innerPadding ->
        ElevatedCard(
            modifier = Modifier.padding(innerPadding),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                text = "Enter your Address Details",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp
            )
            /**
             * This row is for name and pinCode field.
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                /**
                 * Name field
                 */
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = addNewAddressScreenViewModel.name,
                    onValueChange = { value ->
                        addNewAddressScreenViewModel.updateNameState(userName = value)
                    },
                    label = {
                        Text(
                            text = "Name*",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    isError = nameError,
                    supportingText = {
                        nameErrorCause?.let {
                            Text(text = it)
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyMedium
                )

                HorizontalSpace(space = 10)
                /**
                 * PinCode Field
                 */
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = addNewAddressScreenViewModel.pinCode,
                    onValueChange = { value ->
                        addNewAddressScreenViewModel.updateStates(
                            AddressScreenUserActions.OnPinCodeClick(pinCode = value)
                        )
                    },
                    label = {
                        Text(
                            text = "PinCode*",
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
            }
            /**
             * This row is for city and state field.
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                /**
                 * City field
                 */
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = addNewAddressScreenViewModel.city,
                    onValueChange = { value ->
                        addNewAddressScreenViewModel.updateStates(
                            AddressScreenUserActions.OnCityClick(
                                city = value
                            )
                        )
                    },
                    label = {
                        Text(
                            text = "City*",
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
                 * State Field
                 */
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = addNewAddressScreenViewModel.state,
                    onValueChange = { value ->
                        addNewAddressScreenViewModel.updateStates(
                            AddressScreenUserActions.OnStateClick(
                                state = value
                            )
                        )
                    },
                    label = {
                        Text(
                            text = "State*",
                            style = MaterialTheme.typography.bodyMedium,
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
             * Field for house number
             */
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                value = addNewAddressScreenViewModel.houseNumber,
                onValueChange = { value ->
                    addNewAddressScreenViewModel.updateStates(
                        AddressScreenUserActions.OnHouseNoClick(
                            houseNo = value
                        )
                    )
                },
                label = {
                    Text(
                        text = "House Number*",
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
             * Field for road or area
             */
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                value = addNewAddressScreenViewModel.roadOrArea,
                onValueChange = { value ->
                    addNewAddressScreenViewModel.updateStates(
                        AddressScreenUserActions.OnRoadOrAreaClick(
                            roadOrArea = value
                        )
                    )
                },
                label = {
                    Text(
                        text = "Road or Area*",
                        style = MaterialTheme.typography.bodyMedium,
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
            /**
             * Filter Chip for type of Address.
             */
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp),
                text = "Type of Address",
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                /**
                 * Filter Chip for 1st Home.
                 */
                FilterChip(
                    selected = addNewAddressScreenViewModel.typeOfAddress.contains(HOME),
                    onClick = {
                        addNewAddressScreenViewModel.updateStates(
                            AddressScreenUserActions.OnTypeOfAddressClick(
                                typeOfAddress = HOME
                            )
                        )
                    },
                    label = {
                        Text(
                            text = "Home",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    leadingIcon = {
                        if (addNewAddressScreenViewModel.typeOfAddress.contains(HOME)) {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
                        } else {
                            Icon(imageVector = Icons.Outlined.Home, contentDescription = "Home")
                        }
                    }
                )
                HorizontalSpace(space = 15)
                /**
                 * Filter Chip for Office.
                 */
                FilterChip(
                    selected = addNewAddressScreenViewModel.typeOfAddress.contains(OFFICE),
                    onClick = {
                        addNewAddressScreenViewModel.updateStates(
                            AddressScreenUserActions.OnTypeOfAddressClick(
                                typeOfAddress = OFFICE
                            )
                        )
                    },
                    label = {
                        Text(
                            text = "Office",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    leadingIcon = {
                        if (addNewAddressScreenViewModel.typeOfAddress.contains(OFFICE)) {
                            Icon(
                                imageVector = Icons.Filled.Apartment,
                                contentDescription = "Office"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Apartment,
                                contentDescription = "Office"
                            )
                        }
                    }
                )
            }
        }
    }
}