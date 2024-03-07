package com.example.shopkaro.ui.registration_screen

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.shopkaro.R
import com.example.shopkaro.components.HorizontalSpace
import com.example.shopkaro.user_actions_and_web_request_handling.PersonalInfoScreenUserActions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInfoScreen(
    modifier: Modifier,
    backToLogin: () -> Unit,
    addAddress: () -> Unit,
    personalInfoScreenViewModel: PersonalInfoScreenViewModel
) {
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    var nameError by remember {
        mutableStateOf(false)
    }
    var emailError by remember {
        mutableStateOf(false)
    }
    var passwordError by remember {
        mutableStateOf(false)
    }
    var confirmPasswordError by remember {
        mutableStateOf(false)
    }

    var nameErrorText by remember {
        mutableStateOf<String?>(null)
    }
    var emailErrorText by remember {
        mutableStateOf<String?>(value = null)
    }
    var passwordErrorText by remember {
        mutableStateOf<String?>(null)
    }
    var confirmPasswordErrorText by remember {
        mutableStateOf<String?>(null)
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            Button(onClick = {
                /**
                - Do Validation
                 */
                // 1st validation of Name field.
                val regex = Regex("^[A-Za-z][A-Z a-z]{2,49}$")
                if (personalInfoScreenViewModel.name.isNotEmpty()) {
                    if (personalInfoScreenViewModel.name.matches(regex)) {
                        nameError = false
                        nameErrorText = null
                    } else {
                        nameError = true
                        nameErrorText = "Name must contain alphabet only."
                    }
                } else {
                    nameError = true
                    nameErrorText = "Please enter your name"
                }
                // Validation of Email-ID field.
                if (personalInfoScreenViewModel.emailId.isNotEmpty()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(personalInfoScreenViewModel.emailId)
                            .matches()
                    ) {
                        emailError = false
                        emailErrorText = null
                    } else {
                        emailError = true
                        emailErrorText = "Please enter valid email without spaces"
                    }
                } else {
                    emailError = true
                    emailErrorText = "Please enter valid email"
                }
                // Validation of New and Confirm Password Field
                if (personalInfoScreenViewModel.newPassword.isNotEmpty() && personalInfoScreenViewModel.confirmPassword.isNotEmpty()) {

                    if ((personalInfoScreenViewModel.newPassword.length in 6..15) && (personalInfoScreenViewModel.confirmPassword.length in 6..15)) {
                        if (personalInfoScreenViewModel.newPassword == personalInfoScreenViewModel.confirmPassword) {
                            passwordError = false
                            passwordErrorText = null

                            confirmPasswordError = false
                            confirmPasswordErrorText = null
                        } else {
                            // passwords are matching or not
                            passwordError = true
                            passwordErrorText = "Passwords are not matching"

                            confirmPasswordError = true
                            confirmPasswordErrorText = "Passwords are not matching"
                        }
                    } else {
                        // Passwords length are 6 or 15 char
                        passwordError = personalInfoScreenViewModel.newPassword.length !in 6..15
                        passwordErrorText =
                            if (personalInfoScreenViewModel.newPassword.length in 6..15) null else "Password length should be 6 to 15 characters"

                        confirmPasswordError =
                            personalInfoScreenViewModel.confirmPassword.length !in 6..15
                        confirmPasswordErrorText =
                            if (personalInfoScreenViewModel.confirmPassword.length in 6..15) null else "Password length should be 6 to 15 characters"

                    }
                } else {
                    // Passwords are empty or not
                    passwordError = personalInfoScreenViewModel.newPassword.isEmpty()
                    passwordErrorText =
                        if (personalInfoScreenViewModel.newPassword.isEmpty()) "Please enter your new password" else null

                    confirmPasswordError = personalInfoScreenViewModel.confirmPassword.isEmpty()
                    confirmPasswordErrorText =
                        if (personalInfoScreenViewModel.confirmPassword.isEmpty()) "Please enter your new password" else null
                }
                if (nameError || emailError || passwordError || confirmPasswordError) {
                    return@Button
                } else {
                    addAddress()
                }
            }
            ) {
                Text(text = "Next")
            }

        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.personal_info),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { innerPadding ->

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 15.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.personal_info2),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleSmall
                )
                /**
                 * Field for Name
                 */
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = personalInfoScreenViewModel.name,
                    onValueChange = { value ->
                        personalInfoScreenViewModel.setsState(
                            PersonalInfoScreenUserActions.OnNameClick(
                                name = value
                            )
                        )
                    },
                    label = {
                        Text(
                            text = "Enter your name",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    isError = nameError,
                    supportingText = {
                        nameErrorText?.let {
                            Text(text = it)
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                /**
                 * Field for Email ID
                 */
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = personalInfoScreenViewModel.emailId,
                    onValueChange = { value ->
                        personalInfoScreenViewModel.setsState(
                            PersonalInfoScreenUserActions.OnEmailId(
                                emailId = value
                            )
                        )
                    },
                    label = {
                        Text(
                            text = "Enter your Email-ID",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    isError = emailError,
                    supportingText = {
                        emailErrorText?.let {
                            Text(text = it)
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                /**
                 * Field for New Password
                 */
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = personalInfoScreenViewModel.newPassword,
                    onValueChange = { value ->
                        personalInfoScreenViewModel.setsState(
                            PersonalInfoScreenUserActions.OnNewPasswordClick(
                                newPassword = value
                            )
                        )
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    },
                    label = {
                        Text(
                            text = "Enter your New Password",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    isError = passwordError,
                    supportingText = {
                        passwordErrorText?.let {
                            Text(text = it)
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                /**
                 * Field for Confirm Password
                 */
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = personalInfoScreenViewModel.confirmPassword,
                    onValueChange = { value ->
                        personalInfoScreenViewModel.setsState(
                            PersonalInfoScreenUserActions.OnConfirmPasswordClick(
                                confirmPassword = value
                            )
                        )
                    },
                    label = {
                        Text(
                            text = "Enter your Confirm Password",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    isError = confirmPasswordError,
                    supportingText = {
                        confirmPasswordErrorText?.let {
                            Text(text = it)
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyMedium
                )

                /**
                 * If account is already existed, navigate back to login screen
                 */
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Already have an account!Then",
                        style = MaterialTheme.typography.titleSmall
                    )
                    HorizontalSpace(space = 4)
                    Text(
                        modifier = Modifier.clickable {
                            backToLogin()
                        },
                        text = "Login",
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}

