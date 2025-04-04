package com.example.rapikids.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.rapikids.R // Asegúrate de tener R importado correctamente

@Composable
fun RegisterScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var termsChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_hand_wave),
                contentDescription = "User Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Bienvenid@",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = { navController.navigate("login") }) {
                Text("Ingresar", color = MaterialTheme.colorScheme.primary)
            }
            TextButton(onClick = { /* No es necesario en esta pantalla */ }) {
                Text("Registrarse", color = MaterialTheme.colorScheme.secondary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))


        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = termsChecked,
                onCheckedChange = { termsChecked = it }
            )
            Text("Términos y condiciones")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate(Screen.Home.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary), // Rosa fuerte
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Registrarme", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("O registrate con")

        Spacer(modifier = Modifier.height(8.dp))

       
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_facebook),
                contentDescription = "Facebook Icon",
                modifier = Modifier.size(32.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_email),
                contentDescription = "Email Icon",
                modifier = Modifier.size(32.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_instagram),
                contentDescription = "Instagram Icon",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}