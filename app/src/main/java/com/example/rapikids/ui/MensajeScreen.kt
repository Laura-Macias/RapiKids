package com.example.rapikids.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rapikids.R
import com.example.rapikids.ui.Screen

@Composable
fun MensajeScreen(navController: NavController, padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Estos son tus mensajes",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        OutlinedTextField(
            value = "",
            onValueChange = {},
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
            placeholder = { Text("Buscar mensajes") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )


        MessageItem(
            navController = navController,
            profileImage = R.drawable.usuario,
            name = "Prof. Rosalba Cruz",
            message = "Guarderia Niños felices"
        )
        MessageItem(
            navController = navController,
            profileImage = R.drawable.usuario,
            name = "Prof. Ester Ruiz",
            message = "Guarderia Alegria"
        )
        MessageItem(
            navController = navController,
            profileImage = R.drawable.usuario,
            name = "Prof. Ester Ruiz",
            message = "Guarderia Alegria"
        )
        MessageItem(
            navController = navController,
            profileImage = R.drawable.usuario,
            name = "Team Unicorns",
            message = "last seen 45 minutes ago"
        )
        MessageItem(
            navController = navController,
            profileImage = R.drawable.usuario,
            name = "Team Unicorns",
            message = "last seen 45 minutes ago"
        )
        MessageItem(
            navController = navController,
            profileImage = R.drawable.usuario,
            name = "Team Unicorns",
            message = "last seen 45 minutes ago"
        )
    }
}

@Composable
fun MessageItem(navController: NavController, profileImage: Int, name: String, message: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable {
                navController.navigate(Screen.Chat.route)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = profileImage),
            contentDescription = "Profile Image",
            modifier = Modifier.size(48.dp)
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(text = name, style = MaterialTheme.typography.bodyLarge)
            Text(text = message, style = MaterialTheme.typography.bodyMedium)
        }
    }
}