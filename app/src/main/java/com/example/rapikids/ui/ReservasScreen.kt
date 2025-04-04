package com.example.rapikids.ui.screens

import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rapikids.R
import com.example.rapikids.ui.Screen
import java.util.Calendar

@Composable
fun ReservasScreen(navController: NavController, padding: PaddingValues) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    var fechaSeleccionada by remember { mutableStateOf("") }
    var entretenimientoSeleccionado by remember { mutableStateOf(false) }
    var educacionSeleccionado by remember { mutableStateOf(false) }
    var horaEntrada by remember { mutableStateOf("") }
    var horaSalida by remember { mutableStateOf("") }

    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                fechaSeleccionada = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            },
            year,
            month,
            day
        )
    }

    val timePickerDialogEntrada = remember {
        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                horaEntrada = String.format("%02d:%02d", selectedHour, selectedMinute)
            },
            hour,
            minute,
            true
        )
    }

    val timePickerDialogSalida = remember {
        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                horaSalida = String.format("%02d:%02d", selectedHour, selectedMinute)
            },
            hour,
            minute,
            true
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Escoge la fecha de tu reserva",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(onClick = { datePickerDialog.show() }) {
            Text(text = "Seleccionar fecha")
        }

        if (fechaSeleccionada.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Fecha seleccionada: $fechaSeleccionada",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Que servicio deseas hoy",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = entretenimientoSeleccionado,
                onCheckedChange = {
                    entretenimientoSeleccionado = it
                    if (it) educacionSeleccionado = false
                }
            )
            Column {
                Text(text = "Entretenimiento")
                Text(text = "Aulas ludicas para diversión", style = MaterialTheme.typography.bodySmall)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = educacionSeleccionado,
                onCheckedChange = {
                    educacionSeleccionado = it
                    if (it) entretenimientoSeleccionado = false
                }
            )
            Column {
                Text(text = "Educación")
                Text(text = "Aulas de repaso y aprendizaje", style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Valor hora: $25.000",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Indica la hora",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.dinosaurio),
                contentDescription = "Dinosaurio",
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = { timePickerDialogEntrada.show() }) {
                Text(text = "Entrada")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { timePickerDialogSalida.show() }) {
                Text(text = "Salida")
            }
        }

        if (horaEntrada.isNotEmpty() || horaSalida.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            if (horaEntrada.isNotEmpty()) {
                Text(text = "Hora de entrada: $horaEntrada")
            }
            if (horaSalida.isNotEmpty()) {
                Text(text = "Hora de salida: $horaSalida")
            }
        }
        Text(
            text = "Escoge la guardería",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ubicacion),
                contentDescription = "Ubicación",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Ubicación actual")
        }
        Button(
            onClick = { navController.navigate(Screen.ResumenServicio.route)},
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Next")
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = "Flecha",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }

}