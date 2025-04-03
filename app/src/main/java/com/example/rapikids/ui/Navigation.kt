package com.example.rapikids.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
}

@Composable
fun RapiKidsNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController) //
        }
        composable(Screen.Login.route) {
            LoginScreen(navController) //
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController) //
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
    }
}
