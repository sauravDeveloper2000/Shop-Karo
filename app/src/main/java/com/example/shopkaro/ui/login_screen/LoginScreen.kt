package com.example.shopkaro.ui.login_screen

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.shopkaro.R
import com.example.shopkaro.user_actions_and_web_request_handling.LoginScreenUserActions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier,
    createAccount: () -> Unit,
    loginScreenViewModel: LoginScreenViewModel
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    var emailError by remember {
        mutableStateOf(false)
    }
    var passwordError by remember {
        mutableStateOf(false)
    }
    var emailErrorText by remember {
        mutableStateOf<String?>(value = null)
    }
    var passwordErrorText by remember {
        mutableStateOf<String?>(null)
    }
    var context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge
                )
            })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                modifier = Modifier,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        style = MaterialTheme.typography.titleMedium
                    )
                    /**
                     * OutlinedTextField for Email-ID
                     */
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = loginScreenViewModel.userEmail,
                        onValueChange = { value ->
                            loginScreenViewModel.onUserAction(
                                LoginScreenUserActions.OnEmailFieldClick(
                                    value
                                )
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.login_field),
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
                     * OutlinedTextField for Password
                     */
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = loginScreenViewModel.password,
                        onValueChange = { value ->
                            loginScreenViewModel.onUserAction(
                                LoginScreenUserActions.OnPasswordFieldClick(
                                    value
                                )
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.password_field),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image =
                                if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(imageVector = image, contentDescription = null)
                            }
                        },
                        isError = passwordError,
                        supportingText = {
                            passwordErrorText?.let {
                                Text(text = it)
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                    /**
                     * Button - upon click causes login.
                     */
                    Button(
                        onClick = {
                            // Validation of Email-ID field.
                            if (loginScreenViewModel.userEmail.isNotEmpty()) {
                                if (Patterns.EMAIL_ADDRESS.matcher(loginScreenViewModel.userEmail)
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

                            // Validation of password field
                            if (loginScreenViewModel.password.isNotEmpty()) {
                                if (loginScreenViewModel.password.length in 6..15) {
                                    passwordError = false
                                    passwordErrorText = null
                                } else {
                                    passwordError = true
                                    passwordErrorText = "Password length should be 6 to 15 chars"
                                }
                            } else {
                                passwordError = true
                                passwordErrorText = "Please enter your password"
                            }

                            if (emailError || passwordError) {
                                return@Button
                            } else {
                                loginScreenViewModel.logIn(
                                    onSuccess = {
                                        emailError = false
                                        emailErrorText = null
                                        passwordError = false
                                        passwordErrorText = null
                                        Toast.makeText(
                                            context,
                                            "Successfully login",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        loginScreenViewModel.resetStates()
                                    },
                                    onFailure = {
                                        emailError = true
                                        emailErrorText = "Check Email ID"
                                        passwordError = true
                                        passwordErrorText = "Check your Password"
                                        Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                )
                            }
                        },
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.login),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.login_fail),
                            style = MaterialTheme.typography.titleSmall
                        )
                        /**
                         * Text message if user don't have an account.
                         */
                        Text(
                            modifier = Modifier
                                .clickable {
                                    createAccount()
                                    loginScreenViewModel.resetStates()
                                },
                            text = stringResource(id = R.string.create),
                            textDecoration = TextDecoration.Underline,
                            color = Color.Blue,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
        }
    }
}