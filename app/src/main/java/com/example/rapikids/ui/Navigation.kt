package com.example.rapikids.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.*
import com.example.rapikids.ui.components.TopBar
import com.example.rapikids.ui.screens.HomeScreen
import com.example.rapikids.ui.screens.ReservasScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Reservas : Screen("reservas")
}

@Composable
fun RapiKidsNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Home.route) {
            Scaffold(
                topBar = { TopBar(navController) }
            ) { padding ->
                HomeScreen(navController, padding)
            }
        }
        composable(Screen.Reservas.route) {
            Scaffold(
                topBar = { TopBar(navController) }
            ) { padding ->
                ReservasScreen(padding)
            }
        }


        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
    }
}
