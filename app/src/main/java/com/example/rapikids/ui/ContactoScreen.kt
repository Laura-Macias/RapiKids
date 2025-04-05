package com.example.rapikids.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rapikids.R
import com.example.rapikids.ui.Screen

data class Contact(val name: String, val phone: String, val relation: String, val imageResource: Int)

@Composable
fun ContactoScreen(navController: NavController, padding: PaddingValues) {
    val contacts = remember {
        listOf(
            Contact("Jose Ramirez", "3025545555", "Papá", R.drawable.usuario),

        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "En caso de emergencia llamar a",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        contacts.forEach { contact ->
            ContactItem(contact = contact)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(onClick = { navController.navigate(Screen.AgregarContacto.route) }) {
            Text("Agregar contacto")
        }
    }
}

@Composable
fun ContactItem(contact: Contact) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = contact.imageResource),
            contentDescription = "Imagen de perfil de ${contact.name}",
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = contact.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(text = "Celular: ${contact.phone}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Parentesco: ${contact.relation}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}