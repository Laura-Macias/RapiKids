package com.example.rapikids.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rapikids.R
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

data class Reserva(
    val id: String = "",
    val fechaReserva: String = "",
    val horaEntrada: String = "",
    val horaSalida: String = "",
    val servicio: String = "",
    val guarderia: String = "",
    val metodoPago: String = "",
    val estadoPago: String? = null
)

@Composable
fun GuardadoScreen(padding: PaddingValues, navController: NavController) {
    var showPagarDialog by remember { mutableStateOf(false) }
    var reservaAPagarId by remember { mutableStateOf("") }
    val reservasGuardadas = remember { mutableStateListOf<Reserva>() }
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val userId = auth.currentUser?.uid

    LaunchedEffect(key1 = userId) {
        userId?.let { uid ->
            val reservasRef = database.reference.child("reservas").child(uid)
            reservasRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    reservasGuardadas.clear()
                    for (reservaSnapshot in snapshot.children) {
                        val reserva = reservaSnapshot.getValue(Reserva::class.java)
                        reserva?.let {
                            reservasGuardadas.add(it.copy(id = reservaSnapshot.key ?: ""))
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    fun pagarReserva(reservaId: String) {
        userId?.let { uid ->
            val reservaRef = database.reference.child("reservas").child(uid).child(reservaId)
            reservaRef.child("estadoPago").setValue("pagado")
                .addOnSuccessListener {
                    showPagarDialog = true
                    reservaAPagarId = reservaId
                }
                .addOnFailureListener {

                }
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
            text = "Estas son tus reservas",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )

        if (reservasGuardadas.isEmpty()) {
            Text("No tienes reservas guardadas.")
        } else {
            reservasGuardadas.forEach { reserva ->
                ReservaItem(reserva = reserva, onPagarClick = {
                    reservaAPagarId = it
                    pagarReserva(it)
                })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_warning),
                contentDescription = "Advertencia",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Las reservas que aún no se han pagado,\nno se tendrán en cuenta",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        if (showPagarDialog && reservaAPagarId.isNotEmpty()) {
            AlertDialog(
                onDismissRequest = {
                    showPagarDialog = false
                    reservaAPagarId = ""
                },
                title = { Text("Pago exitoso") },
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_aplauso),
                            contentDescription = "Aplauso",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("¡Tu pago se ha realizado con éxito!")
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        showPagarDialog = false
                        reservaAPagarId = ""
                        navController.navigate("home")
                    }) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}

@Composable
fun ReservaItem(reserva: Reserva, onPagarClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = reserva.fechaReserva,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(Icons.Filled.Edit, contentDescription = "Editar")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Hora: ${reserva.horaEntrada} - ${reserva.horaSalida}")
            Text(text = "Guardería: ${reserva.guarderia.substringBefore(" - ")}")
            Text(text = "${reserva.guarderia.substringAfter(" - ")}")
            Text(text = "Servicio: ${reserva.servicio}")
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favorito")
                    Spacer(modifier = Modifier.width(4.dp))
                    if (reserva.estadoPago == "pagado") {
                        Text(
                            text = "Ya se realizó el pago",
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                if (reserva.estadoPago != "pagado") {
                    Button(onClick = { onPagarClick(reserva.id) }) {
                        Text(text = "Pagar")
                    }
                }
            }
        }
    }
}



@Composable
fun rememberNavControllerForPreview(): NavController {
    val context = androidx.compose.ui.platform.LocalContext.current
    return remember {
        object : NavController(context) {
            override fun navigate(deepLink: Uri) {
                val route = ""
                println("Preview Navigation to: $route")
            }

            override fun navigateUp(): Boolean {
                println("Preview Navigate Up")
                return false
            }
        }
    }
}