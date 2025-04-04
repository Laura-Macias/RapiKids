package com.example.rapikids.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rapikids.R
import com.example.rapikids.ui.Screen

@Composable
fun MenuScreen(padding: PaddingValues, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.usuario),
                contentDescription = "Imagen de perfil",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Laura Lizeth Macias",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Lmacias@gmail.com",
                    fontSize = 14.sp
                )
            }
        }


        MenuButton(text = "Mensajes", icon = R.drawable.ic_messages) {
            navController.navigate(Screen.Mensaje.route)
        }
        MenuButton(text = "Contactos agregados", icon = R.drawable.ic_contacts) {

        }
        MenuButton(text = "Reservas", icon = R.drawable.ic_reservations) {

        }
        MenuButton(text = "Ajustes", icon = R.drawable.ic_settings) {

        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                navController.navigate(Screen.Login.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Cerrar sesión")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {  },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text(text = "Eliminar cuenta")
        }
    }
}

@Composable
fun MenuButton(text: String, icon: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text)
        }
    }
}