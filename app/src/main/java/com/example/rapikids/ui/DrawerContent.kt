package com.example.rapikids.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rapikids.ui.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun DrawerContent(navController: NavController, onItemClick: (Screen) -> Unit) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val userId = auth.currentUser?.uid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "RapiKids",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Divider()
        DrawerItem(screen = Screen.Home, label = "Inicio", onItemClick = onItemClick)
        DrawerItem(screen = Screen.Reservas, label = "Realizar reserva", onItemClick = onItemClick)
        DrawerItem(screen = Screen.Mensaje, label = "Mensajes", onItemClick = onItemClick)
        DrawerItem(screen = Screen.Guardado, label = "Reservas guardadas", onItemClick = onItemClick)
        DrawerItem(screen = Screen.Entretenimiento, label = "Entretenimiento", onItemClick = onItemClick)
        DrawerItem(screen = Screen.Educacion, label = "Educación", onItemClick = onItemClick)
        DrawerItem(screen = Screen.Chat, label = "Chat", onItemClick = onItemClick)
        DrawerItem(screen = Screen.Contacto, label = "Contactos", onItemClick = onItemClick)
        DrawerItem(screen = Screen.Login, label = "Cerrar sesión", onItemClick = { showLogoutDialog = true })
        DrawerItem(screen = Screen.Register, label = "Eliminar Cuenta", onItemClick = { showDeleteDialog = true })
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar Cuenta") },
            text = { Text("¿Estás seguro de que deseas eliminar tu cuenta? Esta acción es irreversible.") },
            confirmButton = {
                Button(onClick = {
                    userId?.let { uid ->
                        database.reference.child("usuarios").child(uid).removeValue { error, _ ->
                            if (error == null) {
                                auth.currentUser?.delete()?.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        showDeleteDialog = false
                                        navController.navigate("register") {
                                            popUpTo("home") { inclusive = true }
                                        }
                                    } else {
                                        Log.e("DrawerContent", "Error al eliminar la cuenta: ${task.exception?.message}")
                                    }
                                }
                            } else {
                                Log.e("DrawerContent", "Error al eliminar datos de usuario: ${error.message}")
                            }
                        }
                    } ?: run {
                        showDeleteDialog = false
                        navController.navigate("register") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("No")
                }
            }
        )
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Cerrar Sesión") },
            text = { Text("¿Estás seguro de que deseas cerrar sesión?") },
            confirmButton = {
                Button(onClick = {
                    showLogoutDialog = false
                    auth.signOut()
                    onItemClick(Screen.Login)
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(onClick = { showLogoutDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}

@Composable
fun DrawerItem(screen: Screen, label: String, onItemClick: (Screen) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(screen) }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}