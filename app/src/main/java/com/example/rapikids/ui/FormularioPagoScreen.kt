package com.example.rapikids.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FormularioPagoScreen(
    onDatosTarjetaChange: (numero: String, fecha: String, cvv: String, nombre: String) -> Unit
) {
    var numeroTarjeta by remember { mutableStateOf("") }
    var fechaExpiracion by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var nombreTitular by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ingrese los datos de su tarjeta",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = numeroTarjeta,
            onValueChange = {
                numeroTarjeta = it
                onDatosTarjetaChange(numeroTarjeta, fechaExpiracion, cvv, nombreTitular)
            },
            label = { Text("Número de tarjeta") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = fechaExpiracion,
                onValueChange = {
                    fechaExpiracion = it
                    onDatosTarjetaChange(numeroTarjeta, fechaExpiracion, cvv, nombreTitular)
                },
                label = { Text("Fecha de expiración (MM/AA)") },
                modifier = Modifier.weight(0.6f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = cvv,
                onValueChange = {
                    cvv = it
                    onDatosTarjetaChange(numeroTarjeta, fechaExpiracion, cvv, nombreTitular)
                },
                label = { Text("CVV") },
                modifier = Modifier.weight(0.4f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = nombreTitular,
            onValueChange = {
                nombreTitular = it
                onDatosTarjetaChange(numeroTarjeta, fechaExpiracion, cvv, nombreTitular)
            },
            label = { Text("Nombre del titular") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

    }
}