package com.example.rapikids.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rapikids.ui.Screen

@Composable
fun DrawerContent(onItemClick: (Screen) -> Unit) {
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
        DrawerItem(screen = Screen.Reservas, label = "Reservas", onItemClick = onItemClick)
        DrawerItem(screen = Screen.Mensaje, label = "Mensajes", onItemClick = onItemClick)
        DrawerItem(screen = Screen.Guardado, label = "Guardados", onItemClick = onItemClick)
        DrawerItem(screen = Screen.Entretenimiento, label = "Entretenimiento", onItemClick = onItemClick)
        DrawerItem(screen = Screen.Educacion, label = "Educación", onItemClick = onItemClick)
        DrawerItem(screen = Screen.Chat, label = "Chat", onItemClick = onItemClick)
        DrawerItem(screen = Screen.Contacto, label = "Contactos", onItemClick = onItemClick)
        DrawerItem(screen = Screen.Login, label = "Cerrar sesión", onItemClick = onItemClick)
        // Agrega más elementos del menú aquí
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
        // Puedes agregar un icono aquí si lo deseas
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}