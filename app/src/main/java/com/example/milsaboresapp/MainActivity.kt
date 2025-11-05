package com.example.milsaboresapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.milsaboresapp.navigation.AppRoute
import com.example.milsaboresapp.navigation.NavigationEvent
import com.example.milsaboresapp.ui.theme.MilSaboresAppTheme
import com.example.milsaboresapp.ui.theme.screens.DetailScreen
import com.example.milsaboresapp.ui.theme.screens.HomeScreen
import com.example.milsaboresapp.ui.theme.screens.ProfileScreen
import com.example.milsaboresapp.ui.theme.screens.RegistroScreen
import com.example.milsaboresapp.viewmodel.MainViewModel
import com.example.milsaboresapp.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MilSaboresAppTheme {
                val viewModel: MainViewModel = viewModel()
                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    viewModel.navEvents.collectLatest {
                        event ->
                        when (event) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(event.appRoute.route) {
                                    event.popUpRoute?.let {
                                        popUpTo(it.route) {
                                            inclusive = event.inclusive
                                        }
                                        launchSingleTop = event.singleTop
                                        restoreState = true
                                    }
                                }
                            }
                            is NavigationEvent.NavigateUp -> navController.navigateUp()
                            is NavigationEvent.PopBackStack -> navController.popBackStack()
                        }
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) {
                    innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AppRoute.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(AppRoute.Home.route) {
                            HomeScreen(viewModel, navController)
                        }
                        composable(AppRoute.Register.route) {
                            val viewModelRegistro: UsuarioViewModel = viewModel()
                            RegistroScreen(viewModelRegistro, navController)
                        }
                        composable(AppRoute.Profile.route) {
                            ProfileScreen(viewModel, navController)
                        }
                        composable(AppRoute.Settings.route) {}
                        composable(
                            route = AppRoute.Detail("").route,
                            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
                            DetailScreen(itemId = itemId)
                        }
                    }
                }
            }
        }
    }
}