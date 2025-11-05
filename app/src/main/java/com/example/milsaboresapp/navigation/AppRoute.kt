package com.example.milsaboresapp.navigation

sealed class AppRoute(val route: String) {
    object Home : AppRoute("home")
    object Register : AppRoute("register")
    object Profile : AppRoute("profile")
    object Settings : AppRoute("settings")

    data class Detail(val itemId: String) : AppRoute("detail/{itemId}") {
        fun buildRoute(): String {
            return route.replace("{itemId}", itemId)
        }
    }
}
