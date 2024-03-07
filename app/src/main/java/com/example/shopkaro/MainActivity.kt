package com.example.shopkaro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.shopkaro.components.debugLog
import com.example.shopkaro.navigation.AppNavigation
import com.example.shopkaro.navigation.Destinations
import com.example.shopkaro.ui.account_screen.account_section.AccountScreenViewModel
import com.example.shopkaro.ui.cart_screen.CartScreenViewModel
import com.example.shopkaro.ui.login_screen.LoginScreenViewModel
import com.example.shopkaro.ui.product_detail_screen.ProductDetailScreenViewModel
import com.example.shopkaro.ui.registration_screen.PersonalInfoScreenViewModel
import com.example.shopkaro.ui.start_destination.StartDestinationViewModel
import com.example.shopkaro.ui.theme.ShopKaroTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private val startDestinationViewModel: StartDestinationViewModel by viewModels()
    private val personalInfoScreenViewModel: PersonalInfoScreenViewModel by viewModels()
    private val loginScreenViewModel: LoginScreenViewModel by viewModels()
    private val accountScreenViewModel: AccountScreenViewModel by viewModels()
    private val productDetailScreenViewModel: ProductDetailScreenViewModel by viewModels()
    private val cartScreenViewModel: CartScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = com.google.firebase.Firebase.auth
        setContent {
            ShopKaroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var donePlaying by remember {
                        mutableStateOf(false)
                    }
                    InitialAnimation(
                        modifier = Modifier.fillMaxSize(),
                        onDonePlaying = {
                            donePlaying = true
                        }
                    )
                    if (donePlaying) {
                        AppNavigation(
                            startDestinationViewModel = startDestinationViewModel,
                            personalInfoScreenViewModel = personalInfoScreenViewModel,
                            loginScreenViewModel = loginScreenViewModel,
                            productDetailScreenViewModel = productDetailScreenViewModel,
                            cartScreenViewModel = cartScreenViewModel
                        )
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener { auth ->
            val user = auth.currentUser
            // To check whether user is currently signed in or not. By utilising authStateListener.
            if (user != null) {
                startDestinationViewModel.definesStartDestination(
                    destinations = Destinations.PostAuth
                )
            } else {
                startDestinationViewModel.definesStartDestination(
                    destinations = Destinations.PreAuth
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        accountScreenViewModel.registration.remove()
        debugLog(msg = "Listener Removed")
    }
}

/**
 *  Initial Animation to be shown to user.
 */
@Composable
fun InitialAnimation(
    modifier: Modifier,
    onDonePlaying: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.initial_animation))
    // Whether this lottie is playing or not.
    var isPlaying by remember {
        mutableStateOf(true)
    }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        iterations = 1
    )
    LaunchedEffect(key1 = progress) {
        if (progress == 0f) {
            isPlaying = true
        }
        if (progress == 1f) {
            isPlaying = false
            onDonePlaying()
        }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(composition = composition, progress = { progress })
    }
}
