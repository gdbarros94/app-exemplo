package com.md3demo.ui.navigation

/**
 * Sealed class representing all app destinations
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object Home : Screen("home")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
    object Profile : Screen("profile")
    object SearchResults : Screen("search_results/{query}") {
        fun createRoute(query: String) = "search_results/$query"
    }
    object Cart : Screen("cart")
    object Settings : Screen("settings")
    object Onboarding : Screen("onboarding")
    object Admin : Screen("admin")
    
    companion object {
        const val PRODUCT_ID_ARG = "productId"
        const val QUERY_ARG = "query"
    }
}

/**
 * Bottom navigation destinations
 */
val bottomNavItems = listOf(
    Screen.Home,
    Screen.Profile,
    Screen.Cart,
    Screen.Settings
)