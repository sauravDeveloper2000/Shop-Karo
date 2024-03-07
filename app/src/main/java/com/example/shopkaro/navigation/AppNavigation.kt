package com.example.shopkaro.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.shopkaro.components.debugLog
import com.example.shopkaro.ui.account_screen.address_section.AddNewAddress
import com.example.shopkaro.ui.cart_screen.CartScreenViewModel
import com.example.shopkaro.ui.login_screen.LoginScreen
import com.example.shopkaro.ui.login_screen.LoginScreenViewModel
import com.example.shopkaro.ui.post_auth.PostAuth
import com.example.shopkaro.ui.product_detail_screen.ProductDetailScreenViewModel
import com.example.shopkaro.ui.product_detail_screen.ProductDetails
import com.example.shopkaro.ui.registration_screen.AddressScreen
import com.example.shopkaro.ui.registration_screen.PersonalInfoScreen
import com.example.shopkaro.ui.registration_screen.PersonalInfoScreenViewModel
import com.example.shopkaro.ui.start_destination.StartDestinationViewModel

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestinationViewModel: StartDestinationViewModel,
    personalInfoScreenViewModel: PersonalInfoScreenViewModel,
    loginScreenViewModel: LoginScreenViewModel,
    productDetailScreenViewModel: ProductDetailScreenViewModel,
    cartScreenViewModel: CartScreenViewModel
) {
    val startDestination by startDestinationViewModel._startDestination.collectAsState()

    NavHost(
        navController = navController,
        startDestination = startDestination.route // Destinations.PostAuth.route or Destinations.PreAuth.route
    ) {
        /**
         * PostAuth Composable
         */
        composable(
            route = Destinations.PostAuth.route
        ) {
            PostAuth(
                createNewAddress = {
                    navController.navigate(Destinations.AddNewAddress.route)
                },
                productDetailScreen = {
                    productDetailScreenViewModel.setDesiredProductField(it)
                    navController.navigate(Destinations.ProductDetailScreen.route)
                }
            )
        }

        /**
         * ProductDetailScreen - In this composable user can see product in details.
         */
        composable(
            route = Destinations.ProductDetailScreen.route
        ) {
            productDetailScreenViewModel.desiredProduct?.let { product ->
                debugLog(msg = product.toString())
                ProductDetails(
                    product = product,
                    modifier = Modifier,
                    cartScreenViewModel = cartScreenViewModel
                )
            }
        }
        /**
         * AddNewAddressScreen
         */
        composable(
            route = Destinations.AddNewAddress.route
        ) {
            AddNewAddress(
                modifier = Modifier.fillMaxSize(),
                backToAccountSection = {
                    navController.popBackStack()
                }
            )
        }

        /**
         * Navigation() - To do nested navigation within an navigation.
         */
        navigation(
            route = Destinations.PreAuth.route,
            startDestination = Destinations.PreAuth.LoginScreen.route
        ) {
            composable(
                route = Destinations.PreAuth.LoginScreen.route
            ) {
                LoginScreen(
                    modifier = Modifier.fillMaxSize(),
                    createAccount = {
                        navigateAndPopUp(
                            navController = navController,
                            route = Destinations.PreAuth.RegistrationScreen.route,
                            popUp = Destinations.PreAuth.LoginScreen.route
                        )
                    },
                    loginScreenViewModel = loginScreenViewModel
                )
            }

            /**
             *  Composable for taking user's personal info like name, email and new password.
             */
            composable(
                route = Destinations.PreAuth.RegistrationScreen.route
            ) {
                PersonalInfoScreen(
                    modifier = Modifier.fillMaxSize(),
                    backToLogin = {
                        navigateAndPopUp(
                            navController = navController,
                            route = Destinations.PreAuth.LoginScreen.route,
                            popUp = Destinations.PreAuth.RegistrationScreen.route
                        )
                    },
                    addAddress = {
                        navController.navigate(Destinations.PreAuth.AddressScreen.route)
                    },
                    personalInfoScreenViewModel = personalInfoScreenViewModel
                )
            }

            /**
             * Address Screen composable to take user address info.
             */
            composable(
                route = Destinations.PreAuth.AddressScreen.route
            ) {
                AddressScreen(
                    modifier = Modifier.fillMaxSize(),
                    backToPersonalInfoScreen = {
                        navController.navigate(Destinations.PreAuth.RegistrationScreen.route)
                    },
                    personalInfoScreenViewModel = personalInfoScreenViewModel
                )
            }
        }
    }
}


/**
 * An helper method, which navigates to our desired destination by removing our current destination.
 */
fun navigateAndPopUp(
    navController: NavHostController,
    route: String,
    popUp: String
) {
    navController.navigate(route) {
        launchSingleTop = true
        popUpTo(popUp) { inclusive = true }
    }
}