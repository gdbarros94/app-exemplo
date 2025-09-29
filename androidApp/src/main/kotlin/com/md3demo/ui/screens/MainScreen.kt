package com.md3demo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.md3demo.ui.navigation.Screen

/**
 * Main app navigation with bottom navigation bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    },
                    onSearchClick = { query ->
                        navController.navigate(Screen.SearchResults.createRoute(query))
                    }
                )
            }
            
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            
            composable(Screen.Cart.route) {
                CartScreen(
                    onCheckout = {
                        // Show success message or navigate to success screen
                    }
                )
            }
            
            composable(Screen.Settings.route) {
                SettingsScreen()
            }
            
            composable(Screen.ProductDetail.route) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString(Screen.PRODUCT_ID_ARG)?.toIntOrNull()
                if (productId != null) {
                    ProductDetailScreen(
                        productId = productId,
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    NavigationBar {
        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (screen) {
                            Screen.Home -> Icons.Default.Home
                            Screen.Profile -> Icons.Default.Person
                            Screen.Cart -> Icons.Default.ShoppingCart
                            Screen.Settings -> Icons.Default.Settings
                            else -> Icons.Default.Home
                        },
                        contentDescription = when (screen) {
                            Screen.Home -> "Home"
                            Screen.Profile -> "Profile"
                            Screen.Cart -> "Cart"
                            Screen.Settings -> "Settings"
                            else -> "Home"
                        }
                    )
                },
                label = {
                    Text(
                        text = when (screen) {
                            Screen.Home -> "Home"
                            Screen.Profile -> "Profile"
                            Screen.Cart -> "Cart"
                            Screen.Settings -> "Settings"
                            else -> "Home"
                        }
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

// Simple placeholder screens
@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(
            text = "Profile Screen",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(
            text = "Settings Screen",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun ProductDetailScreen(
    productId: Int,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(
                text = "Product Detail Screen",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Product ID: $productId",
                style = MaterialTheme.typography.bodyMedium
            )
            Button(onClick = onNavigateBack) {
                Text("Go Back")
            }
        }
    }
}

private val bottomNavItems = listOf(
    Screen.Home,
    Screen.Profile,
    Screen.Cart,
    Screen.Settings
)