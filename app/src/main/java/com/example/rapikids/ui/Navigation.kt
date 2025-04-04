package com.example.rapikids.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.*
import com.example.rapikids.ui.components.TopBar
import com.example.rapikids.ui.screens.GuardadoScreen
import com.example.rapikids.ui.screens.HomeScreen
import com.example.rapikids.ui.screens.MensajeScreen
import com.example.rapikids.ui.screens.MenuScreen
import com.example.rapikids.ui.screens.ReservasScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Reservas : Screen("reservas")
<<<<<<< HEAD
=======
    object ResumenServicio : Screen("resumenServicio")
    object Menu : Screen("menu")
    object Mensaje : Screen("mensaje")
    object Guardado : Screen("guardado")
>>>>>>> d285889 (se crea la pantalla Menu,Mensaje,guardado)
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
<<<<<<< HEAD


=======
        composable(Screen.Menu.route) {
            Scaffold(
                topBar = { TopBar(navController) }
            ) { padding ->
                MenuScreen(padding, navController)
            }
        }
        composable(Screen.Mensaje.route) {
            Scaffold(
                topBar = { TopBar(navController) }
            ) { padding ->
                MensajeScreen(padding)
            }
        }
        composable(Screen.Guardado.route) {
            Scaffold(
                topBar = { TopBar(navController) }
            ) { padding ->
                GuardadoScreen(padding, navController)
            }
        }
>>>>>>> d285889 (se crea la pantalla Menu,Mensaje,guardado)
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
