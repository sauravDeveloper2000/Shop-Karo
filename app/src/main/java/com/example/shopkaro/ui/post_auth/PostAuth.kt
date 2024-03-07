package com.example.shopkaro.ui.post_auth

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shopkaro.model_class.Product
import com.example.shopkaro.navigation.Destinations
import com.example.shopkaro.ui.account_screen.account_section.AccountScreen
import com.example.shopkaro.ui.cart_screen.CartScreen
import com.example.shopkaro.ui.category_screen.CategoryScreen
import com.example.shopkaro.ui.category_screen.ProductByCategoryViewModel
import com.example.shopkaro.ui.category_screen.ProductsByCategory
import com.example.shopkaro.ui.home_screen.HomeScreen

/**
 * Data class for bottom nav bar item. To show what data that particular item holds.
 */
data class BottomNavBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

@Composable
fun PostAuth(
    productsByCategoryViewModel: ProductByCategoryViewModel = hiltViewModel(),
    createNewAddress: () -> Unit,
    productDetailScreen: (Product) -> Unit
) {
    val navController = rememberNavController()

    val navBarItems = rememberSaveable {
        listOf(
            BottomNavBarItem(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                route = "Home"
            ),
            BottomNavBarItem(
                title = "Category",
                selectedIcon = Icons.Filled.Category,
                unselectedIcon = Icons.Outlined.Category,
                route = "Category"
            ),
            BottomNavBarItem(
                title = "Account",
                selectedIcon = Icons.Filled.AccountCircle,
                unselectedIcon = Icons.Outlined.AccountCircle,
                route = "Account"
            ),
            BottomNavBarItem(
                title = "Cart",
                selectedIcon = Icons.Filled.ShoppingCart,
                unselectedIcon = Icons.Outlined.ShoppingCart,
                route = "Cart"
            )
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                navBarItems = navBarItems
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Destinations.PostAuth.HomeScreen.route
        ) {
            /**
             * All Products section
             */
            composable(route = Destinations.PostAuth.HomeScreen.route) {
                HomeScreen(
                    getToProductDetail = { product ->
                        productDetailScreen(product)
                    }
                )
            }

            /**
             * Category Section
             */
            composable(route = Destinations.PostAuth.CategoryScreen.route) {
                CategoryScreen(
                    modifier = Modifier.fillMaxSize(),
                    getProductsByCategory = { endPoint ->
                        productsByCategoryViewModel.givesProductsByCategory(category = endPoint)
                        navController.navigate(route = Destinations.PostAuth.ProductsByCategoryScreen.route)
                    }
                )
            }

            /**
             * ProductsByCategory
             */
            composable(
                route = Destinations.PostAuth.ProductsByCategoryScreen.route

            ) {

                ProductsByCategory(
                    modifier = Modifier.fillMaxSize(),
                    productsByCategoryState = productsByCategoryViewModel.productsByCategoryUiState.collectAsState().value,
                    productInDetails = {
                        productDetailScreen(it)
                    }
                )
            }

            /**
             * Account Screen
             */
            composable(route = Destinations.PostAuth.AccountScreen.route) {
                AccountScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    addNewAddress = createNewAddress
                )
            }

            /**
             * Cart Screen Composable
             */
            composable(route = Destinations.PostAuth.CartScreen.route) {
                CartScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    backToHomeScreen = {
                        navController.navigate(Destinations.PostAuth.HomeScreen.route)
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController,
    navBarItems: List<BottomNavBarItem>
) {
    NavigationBar {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination
        navBarItems.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) screen.selectedIcon else screen.unselectedIcon,
                        contentDescription = screen.title
                    )
                },
                label = {
                    Text(text = screen.title)
                }
            )
        }
    }
}