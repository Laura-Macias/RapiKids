package com.example.rapikids.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.*
import com.example.rapikids.ui.components.TopBar
import com.example.rapikids.ui.screens.*

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Reservas : Screen("reservas")
    object ResumenServicio : Screen("resumenServicio")
    object Menu : Screen("menu")
    object Mensaje : Screen("mensaje")
    object Guardado : Screen("guardado")
    object Entretenimiento : Screen("entretenimiento")
    object Educacion : Screen("educacion")
    object Chat : Screen("chat")
}

@Composable
fun RapiKidsNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Menu.route) {
            Scaffold(
                topBar = { TopBar(navController) }
            ) { padding ->
                MenuScreen(navController, padding)
            }
        }
        composable(Screen.Chat.route) {
            Scaffold(
                topBar = { TopBar(navController) }
            ) { padding ->
                ChatScreen(navController, padding)
            }
        }

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
                ReservasScreen(navController, padding)
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
        composable(Screen.ResumenServicio.route) {
            Scaffold(
                topBar = { TopBar(navController) }
            ) { padding ->
                ResumenServicioScreen(navController, padding)
            }
        }
        composable(Screen.Entretenimiento.route) {
            Scaffold(
                topBar = { TopBar(navController) }
            ) { padding ->
                EntretenimientoScreen(navController, padding)
            }
        }
        composable(Screen.Educacion.route) {
            Scaffold(
                topBar = { TopBar(navController) }
            ) { padding ->
                EducacionScreen(navController, padding)
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
