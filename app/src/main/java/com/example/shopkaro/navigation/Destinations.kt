package com.example.shopkaro.navigation

sealed class Destinations(
    val route: String
) {
    data object PreAuth : Destinations(route = "PreAuth") {
        data object RegistrationScreen : Destinations(route = "Registration")
        data object LoginScreen : Destinations(route = "Login")
        data object AddressScreen : Destinations(route = "Address")
    }

    data object PostAuth : Destinations(route = "PostAuth") {
        data object HomeScreen : Destinations(route = "Home")
        data object CategoryScreen : Destinations(route = "Category")
        data object ProductsByCategoryScreen : Destinations(route = "ProductsByCategory")
        data object AccountScreen : Destinations(route = "Account")
        data object CartScreen : Destinations(route = "Cart")
    }

    data object ProductDetailScreen : Destinations(route = "ProductDetails")
    data object AddNewAddress : Destinations(route = "AddNewAddress")
}
