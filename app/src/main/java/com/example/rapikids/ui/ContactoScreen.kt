package com.example.rapikids.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rapikids.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


data class Contact(
    val id: String = "",
    val nombre: String = "",
    val celular: String = "",
    val parentesco: String = "",
    val imageResource: Int = R.drawable.usuario
) {
    constructor() : this("", "", "", "", R.drawable.usuario)
}

@Composable
fun ContactoScreen(navController: NavController, padding: PaddingValues) {
    val contacts = remember { mutableStateListOf<Contact>() }
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val userId = auth.currentUser?.uid

    LaunchedEffect(key1 = userId) {
        if (userId != null) {
            val contactsRef = database.reference.child("usuarios").child(userId).child("contactos")
            contactsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val fetchedContacts = mutableListOf<Contact>()
                    for (contactSnapshot in snapshot.children) {
                        val contact = contactSnapshot.getValue(Contact::class.java)
                        contact?.let {
                            fetchedContacts.add(it.copy(id = contactSnapshot.key ?: ""))
                        }
                    }
                    contacts.clear()
                    contacts.addAll(fetchedContacts)
                    Log.d("ContactoScreen", "Contactos leídos una vez: $contacts")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ContactoScreen", "Error al leer contactos: ${error.message}")
                }
            })
        }
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

        if (contacts.isEmpty()) {
            Text("No hay contactos guardados.")
        } else {
            contacts.forEach { contact ->
                ContactItem(contact = contact)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Button(onClick = { navController.navigate("agregarContacto") }) {
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
            contentDescription = "Imagen de perfil de ${contact.nombre}",
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = contact.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(
                text = "Celular: ${contact.celular}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = "Parentesco: ${contact.parentesco}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}