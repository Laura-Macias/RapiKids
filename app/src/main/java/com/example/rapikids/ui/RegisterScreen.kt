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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rapikids.R

@Composable
fun RegisterScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var termsChecked by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf("Registrarse") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_hand_wave_register),
                contentDescription = "User Icon",
                modifier = Modifier.size(56.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = "Bienvenid@",
                style = MaterialTheme.typography.headlineSmall
            )
        }




        Spacer(modifier = Modifier.height(24.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = {
                    selectedTab = "Ingresar"
                    navController.navigate(Screen.Login.route)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Ingresar",
                    color = if (selectedTab == "Ingresar") Color(0xFFD81B60) else Color.Gray,
                    fontSize = 18.sp
                )
            }
            TextButton(
                onClick = {
                    selectedTab = "Registrarse"
                    navController.navigate(Screen.Register.route)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Registrarse",
                    color = if (selectedTab == "Registrarse") Color(0xFFD81B60) else Color.Gray,
                    fontSize = 18.sp
                )
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
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD81B60)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Registarse", color = Color.White)
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
                modifier = Modifier.size(42.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_email),
                contentDescription = "Email Icon",
                modifier = Modifier.size(42.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_instagram),
                contentDescription = "Instagram Icon",
                modifier = Modifier.size(42.dp)
            )
        }
    }
}