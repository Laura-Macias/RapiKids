package com.example.rapikids.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rapikids.ui.Screen

@Composable
fun MenuScreen(navController: NavController, padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Text(text = "Menú", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Mensaje",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .clickable { navController.navigate(Screen.Mensaje.route)  }
                .padding(8.dp)
        )
        Text(
            text = "Contactos agregados",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .clickable { navController.navigate(Screen.Contacto.route)  }
                .padding(8.dp)
        )

        Text(
            text = "Reserva",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .clickable { navController.navigate(Screen.Guardado.route)  }
                .padding(8.dp)
        )
        Text(
            text = "salir",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .clickable { navController.navigate(Screen.Home.route)  }
                .padding(8.dp)
        )


    }
}
