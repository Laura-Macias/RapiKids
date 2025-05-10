package com.example.rapikids.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rapikids.R
import com.example.rapikids.ui.FormularioPagoScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenServicioScreen(navController: NavController, padding: PaddingValues) {
    var showGuardarDialog by remember { mutableStateOf(false) }
    var showPagarDialog by remember { mutableStateOf(false) }
    var showFormularioPagoDialog by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val userId = auth.currentUser?.uid

    var fechaReserva by remember { mutableStateOf("") }
    var horaEntrada by remember { mutableStateOf("") }
    var horaSalida by remember { mutableStateOf("") }
    var servicioSeleccionado by remember { mutableStateOf("") }
    var guarderiaSeleccionada by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("Método de pago") }

    // Variables para almacenar los datos de la tarjeta
    var numeroTarjeta by remember { mutableStateOf("") }
    var fechaExpiracionTarjeta by remember { mutableStateOf("") }
    var cvvTarjeta by remember { mutableStateOf("") }
    var nombreTitularTarjeta by remember { mutableStateOf("") }

    LaunchedEffect(key1 = userId) {
        userId?.let { uid ->
            val reservasRef = database.reference.child("reservas_temporales").child(uid)
            reservasRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    fechaReserva = snapshot.child("fechaSeleccionada").getValue(String::class.java) ?: ""
                    horaEntrada = snapshot.child("horaEntrada").getValue(String::class.java) ?: ""
                    horaSalida = snapshot.child("horaSalida").getValue(String::class.java) ?: ""
                    val entretenimiento = snapshot.child("entretenimientoSeleccionado").getValue(Boolean::class.java) ?: false
                    val educacion = snapshot.child("educacionSeleccionado").getValue(Boolean::class.java) ?: false
                    servicioSeleccionado = if (entretenimiento) "Entretenimiento" else if (educacion) "Educación" else ""
                    guarderiaSeleccionada = snapshot.child("lugarSeleccionado").getValue(String::class.java) ?: ""
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }


    fun guardarReservaPermanente() {
        userId?.let { uid ->
            val reservasRef = database.reference.child("reservas").child(uid).push()
            val reservaData = hashMapOf(
                "fechaReserva" to fechaReserva,
                "horaEntrada" to horaEntrada,
                "horaSalida" to horaSalida,
                "servicio" to servicioSeleccionado,
                "guarderia" to guarderiaSeleccionada,
                "metodoPago" to selectedOptionText

            )
            reservasRef.setValue(reservaData)
                .addOnSuccessListener {
                    database.reference.child("reservas_temporales").child(uid).removeValue()
                    showGuardarDialog = true
                }
                .addOnFailureListener {
                }
        }
    }


    fun realizarPago() {
        userId?.let { uid ->
            val reservasRef = database.reference.child("reservas").child(uid).push()
            val reservaData = hashMapOf(
                "fechaReserva" to fechaReserva,
                "horaEntrada" to horaEntrada,
                "horaSalida" to horaSalida,
                "servicio" to servicioSeleccionado,
                "guarderia" to guarderiaSeleccionada,
                "metodoPago" to selectedOptionText,
                "estadoPago" to "pagado"
            )
            reservasRef.setValue(reservaData)
                .addOnSuccessListener {
                    database.reference.child("reservas_temporales").child(uid).removeValue()
                    showPagarDialog = true
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

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_resumen_servicio),
                contentDescription = "Resumen de servicio chica",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Resumen de servicio",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Día de la reserva",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = fechaReserva.ifEmpty { "No seleccionado" },
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Blue
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Hora seleccionada",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Box(
                            modifier = Modifier
                                .background(Color.Green)
                                .padding(8.dp)
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Entrada",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = horaEntrada.ifEmpty { "--:--" },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .background(Color.Red)
                                .padding(8.dp)
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Salida",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = horaSalida.ifEmpty { "--:--" },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_metodo_pago),
                contentDescription = "Método de pago",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                TextField(
                    readOnly = true,
                    value = selectedOptionText,
                    onValueChange = {},
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(
                        text = { Text("PSE") },
                        onClick = {
                            selectedOptionText = "PSE"
                            expanded = false
                            showFormularioPagoDialog = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Tarjeta de crédito") },
                        onClick = {
                            selectedOptionText = "Tarjeta de crédito"
                            expanded = false
                            showFormularioPagoDialog = true
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Tarjeta de débito") },
                        onClick = {
                            selectedOptionText = "Tarjeta de débito"
                            expanded = false
                            showFormularioPagoDialog = true
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = guarderiaSeleccionada.substringBefore(" - ").ifEmpty { "No seleccionada" },
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = guarderiaSeleccionada.substringAfter(" - ").ifEmpty { "" },
                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val valorHora = 25000
        val horasReserva = remember(horaEntrada, horaSalida) {
            if (horaEntrada.isNotEmpty() && horaSalida.isNotEmpty()) {
                val entradaParts = horaEntrada.split(":").map { it.toInt() }
                val salidaParts = horaSalida.split(":").map { it.toInt() }
                val minutosEntrada = entradaParts[0] * 60 + entradaParts[1]
                val minutosSalida = salidaParts[0] * 60 + salidaParts[1]
                (minutosSalida - minutosEntrada) / 60
            } else {
                0
            }
        }
        val totalAPagar = remember(valorHora, horasReserva) {
            valorHora * horasReserva
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "TOTAL A PAGAR:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$${totalAPagar}",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFFFFA500)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { guardarReservaPermanente() },
                modifier = Modifier.weight(1f),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))
            ) {
                Text(text = "Guardar reserva", color = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    if (selectedOptionText == "Tarjeta de crédito" || selectedOptionText == "Tarjeta de débito") {
                        showFormularioPagoDialog = true
                    } else {
                        realizarPago()
                    }
                },
                modifier = Modifier.weight(1f),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Text(text = "Pagar", color = Color.White)
            }
        }

        if (showGuardarDialog) {
            AlertDialog(
                onDismissRequest = { showGuardarDialog = false },
                title = { Text("Tu reserva fue guardada") },
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_pulgar_arriba),
                            contentDescription = "Pulgar arriba",
                            modifier = Modifier.size(24.dp)
                        )

                    }
                },
                confirmButton = {
                    Button(onClick = {
                        showGuardarDialog = false
                        navController.navigate("home")
                    }) {
                        Text("Aceptar")
                    }
                }
            )
        }

        if (showPagarDialog) {
            AlertDialog(
                onDismissRequest = { showPagarDialog = false },
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

                    }
                },
                confirmButton = {
                    Button(onClick = {
                        showPagarDialog = false
                        navController.navigate("home")
                    }) {
                        Text("Aceptar")
                    }
                }
            )
        }


        if (showFormularioPagoDialog) {
            AlertDialog(
                onDismissRequest = { showFormularioPagoDialog = false },
                title = { Text("Detalles de pago") },
                text = {
                    FormularioPagoScreen(
                        onDatosTarjetaChange = { numero, fecha, cvv, nombre ->
                            numeroTarjeta = numero
                            fechaExpiracionTarjeta = fecha
                            cvvTarjeta = cvv
                            nombreTitularTarjeta = nombre
                        }
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        println("Procesando pago con: $numeroTarjeta, $fechaExpiracionTarjeta, $cvvTarjeta, $nombreTitularTarjeta")
                        realizarPago()
                        showFormularioPagoDialog = false
                    }) {
                        Text("Pagar ahora")
                    }
                },
                dismissButton = {
                    Button(onClick = { showFormularioPagoDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}