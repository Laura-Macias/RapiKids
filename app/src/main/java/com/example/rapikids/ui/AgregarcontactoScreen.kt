package com.example.rapikids.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarcontactoScreen(navController: NavController, padding: PaddingValues) {
    var nombre by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var parentesco by remember { mutableStateOf("") }


    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Agregar contacto",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = celular,
            onValueChange = { celular = it },
            label = { Text("Celular") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = parentesco,
            onValueChange = { parentesco = it },
            label = { Text("Parentesco") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Button(onClick = {

            val userId = auth.currentUser?.uid

            if (userId != null) {

                val contacto = hashMapOf(
                    "nombre" to nombre,
                    "celular" to celular,
                    "parentesco" to parentesco
                )


                database.reference.child("usuarios").child(userId).child("contactos").push()
                    .setValue(contacto)
                    .addOnSuccessListener {

                        println("Contacto guardado exitosamente")
                        navController.popBackStack()
                    }
                    .addOnFailureListener { e ->
                        println("Error al guardar el contacto: $e")
                    }
            } else {
                println("Usuario no autenticado")
            }
        }) {
            Text("Guardar")
        }
    }
}