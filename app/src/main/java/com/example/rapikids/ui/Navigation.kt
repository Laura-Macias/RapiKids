package com.example.rapikids.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rapikids.ui.components.DrawerContent
import com.example.rapikids.ui.components.TopBar
import com.example.rapikids.ui.screens.*
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Reservas : Screen("reservas")
    object ResumenServicio : Screen("resumenServicio")
    object Mensaje : Screen("mensaje")
    object Guardado : Screen("guardado")
    object Entretenimiento : Screen("entretenimiento")
    object Educacion : Screen("educacion")
    object Chat : Screen("chat")
    object Contacto : Screen("contacto")
    object AgregarContacto : Screen("agregarContacto")
    object Menu : Screen("menu")
    object RecuperarContrasena : Screen("recuperarContrasena")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RapiKidsNavHost() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var userName by remember { mutableStateOf("") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    onItemClick = { screen ->
                        scope.launch { drawerState.close() }
                        navController.navigate(screen.route)
                    }
                )
            }
        }
    ) {
        NavHost(navController = navController, startDestination = Screen.Splash.route) {
            composable(Screen.Contacto.route) {
                Scaffold(
                    topBar = { TopBar(navController = navController, onMenuClick = { scope.launch { drawerState.open() } }, userName = userName) }
                ) { padding ->
                    ContactoScreen(navController = navController, padding = padding)
                }
            }
            composable(Screen.AgregarContacto.route) {
                Scaffold(
                    topBar = { TopBar(navController = navController, onMenuClick = { scope.launch { drawerState.open() } }, userName = userName) }
                ) { padding ->
                    AgregarcontactoScreen(navController = navController, padding = padding)
                }
            }

            composable(Screen.Chat.route) {
                Scaffold(
                    topBar = { TopBar(navController = navController, onMenuClick = { scope.launch { drawerState.open() } }, userName = userName) }
                ) { padding ->
                    ChatScreen(navController, padding)
                }
            }

            composable(Screen.Home.route) {
                Scaffold(
                    topBar = { TopBar(navController = navController, onMenuClick = { scope.launch { drawerState.open() } }, userName = userName) }
                ) { padding ->
                    HomeScreen(navController, padding) { name -> userName = name }
                }
            }

            composable(Screen.Reservas.route) {
                Scaffold(
                    topBar = { TopBar(navController = navController, onMenuClick = { scope.launch { drawerState.open() } }, userName = userName) }
                ) { padding ->
                    ReservasScreen(navController, padding)
                }
            }

            composable(Screen.Mensaje.route) {
                Scaffold(
                    topBar = { TopBar(navController = navController, onMenuClick = { scope.launch { drawerState.open() } }, userName = userName) }
                ) { padding ->
                    MensajeScreen(navController = navController, padding = padding)
                }
            }

            composable(Screen.Guardado.route) {
                Scaffold(
                    topBar = { TopBar(navController = navController, onMenuClick = { scope.launch { drawerState.open() } }, userName = userName) }
                ) { padding ->
                    GuardadoScreen(padding, navController)
                }
            }
            composable(Screen.ResumenServicio.route) {
                Scaffold(
                    topBar = { TopBar(navController = navController, onMenuClick = { scope.launch { drawerState.open() } }, userName = userName) }
                ) { padding ->
                    ResumenServicioScreen(navController, padding)
                }
            }
            composable(Screen.Entretenimiento.route) {
                Scaffold(
                    topBar = { TopBar(navController = navController, onMenuClick = { scope.launch { drawerState.open() } }, userName = userName) }
                ) { padding ->
                    EntretenimientoScreen(navController, padding)
                }
            }
            composable(Screen.Educacion.route) {
                Scaffold(
                    topBar = { TopBar(navController = navController, onMenuClick = { scope.launch { drawerState.open() } }, userName = userName) }
                ) { padding ->
                    EducacionScreen(navController, padding)
                }
            }

            composable(Screen.Splash.route) {
                SplashScreen(navController)
            }

            composable(Screen.Login.route) {
                LoginScreen(navController) { name -> userName = name }
            }

            composable(Screen.Register.route) {
                RegisterScreen(navController) { name -> userName = name }
            }
            composable(Screen.RecuperarContrasena.route) {
                RecuperarContrasenaScreen(navController)
            }
        }
    }
}