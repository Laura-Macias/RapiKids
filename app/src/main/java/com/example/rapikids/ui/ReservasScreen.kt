package com.example.rapikids.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.rapikids.R
import com.example.rapikids.ui.Screen
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

@Composable
fun ReservasScreen(navController: NavController, padding: PaddingValues) {
    val context = LocalContext.current
    val activity = context as Activity
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    var fechaSeleccionada by remember { mutableStateOf("") }
    var servicioSeleccionado by remember { mutableStateOf<String?>(null) }
    var horaEntrada by remember { mutableStateOf("") }
    var horaSalida by remember { mutableStateOf("") }
    var lugarSeleccionado by remember { mutableStateOf("") }

    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val userId = auth.currentUser?.uid

    val placeLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val place = Autocomplete.getPlaceFromIntent(result.data!!)
            lugarSeleccionado = "${place.name} - ${place.address}"
        }
    }

    val permisoUbicacionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Permiso concedido", Toast.LENGTH_SHORT).show()
            lanzarAutocompleteConUbicacion(fusedLocationClient, placeLauncher, context)
        } else {
            Toast.makeText(context, "Se necesita permiso de ubicación para buscar guarderías cercanas", Toast.LENGTH_LONG).show()
        }
    }

    val datePickerDialog = remember {
        DatePickerDialog(context, { _, y, m, d ->
            fechaSeleccionada = "$d/${m + 1}/$y"
        }, year, month, day)
    }

    val timePickerDialogEntrada = remember {
        TimePickerDialog(context, { _, h, m ->
            horaEntrada = String.format("%02d:%02d", h, m)
        }, hour, minute, true)
    }

    val timePickerDialogSalida = remember {
        TimePickerDialog(context, { _, h, m ->
            horaSalida = String.format("%02d:%02d", h, m)
        }, hour, minute, true)
    }

    fun guardarDatosReservaTemporal() {
        userId?.let { uid ->
            val reservasRef = database.reference.child("reservas_temporales").child(uid)
            val reservaData = hashMapOf(
                "fechaSeleccionada" to fechaSeleccionada,
                "entretenimientoSeleccionado" to (servicioSeleccionado == "entretenimiento"),
                "educacionSeleccionado" to (servicioSeleccionado == "educacion"),
                "horaEntrada" to horaEntrada,
                "horaSalida" to horaSalida,
                "lugarSeleccionado" to lugarSeleccionado
            )
            reservasRef.setValue(reservaData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Datos guardados temporalmente", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Error al guardar datos: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } ?: run {
            Toast.makeText(context, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
        }
    }

    val todosLosCamposLlenos = fechaSeleccionada.isNotEmpty() &&
            servicioSeleccionado != null &&
            horaEntrada.isNotEmpty() &&
            horaSalida.isNotEmpty() &&
            lugarSeleccionado.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Escoge la fecha de tu reserva", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { datePickerDialog.show() }) {
            Text("Seleccionar fecha")
        }

        if (fechaSeleccionada.isNotEmpty()) {
            Text("Fecha seleccionada: $fechaSeleccionada", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("¿Qué servicio deseas hoy?", style = MaterialTheme.typography.headlineSmall)

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = servicioSeleccionado == "entretenimiento",
                    onClick = { servicioSeleccionado = "entretenimiento" }
                )
                Column {
                    Text("Entretenimiento")
                    Text("Aulas lúdicas para diversión", style = MaterialTheme.typography.bodySmall)
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = servicioSeleccionado == "educacion",
                    onClick = { servicioSeleccionado = "educacion" }
                )
                Column {
                    Text("Educación")
                    Text("Aulas de repaso y aprendizaje", style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Valor hora: $25.000", color = Color.Red, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(24.dp))
        Text("Indica la hora", style = MaterialTheme.typography.headlineSmall)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.dinosaurio),
                contentDescription = "Dinosaurio",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { timePickerDialogEntrada.show() }) { Text("Entrada") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { timePickerDialogSalida.show() }) { Text("Salida") }
        }

        if (horaEntrada.isNotEmpty() || horaSalida.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            if (horaEntrada.isNotEmpty()) Text("Hora de entrada: $horaEntrada")
            if (horaSalida.isNotEmpty()) Text("Hora de salida: $horaSalida")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Escoge la guardería", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        lanzarAutocompleteConUbicacion(fusedLocationClient, placeLauncher, context)
                    } else {
                        permisoUbicacionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                }
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ubicacion),
                contentDescription = "Ícono ubicación",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Buscar guarderías cercanas", style = MaterialTheme.typography.bodyLarge)
        }

        if (lugarSeleccionado.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Guardería seleccionada: $lugarSeleccionado",
                color = Color(0xFF4CAF50),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Button(
            onClick = {
                guardarDatosReservaTemporal()
                navController.navigate(Screen.ResumenServicio.route)
            },
            enabled = todosLosCamposLlenos,
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

@SuppressLint("MissingPermission")
private fun lanzarAutocompleteConUbicacion(
    fusedLocationClient: com.google.android.gms.location.FusedLocationProviderClient,
    placeLauncher: androidx.activity.compose.ManagedActivityResultLauncher<android.content.Intent, androidx.activity.result.ActivityResult>,
    context: android.content.Context
) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            val currentLatLng = LatLng(location.latitude, location.longitude)
            val bounds = RectangularBounds.newInstance(
                LatLng(currentLatLng.latitude - 0.03, currentLatLng.longitude - 0.03),
                LatLng(currentLatLng.latitude + 0.03, currentLatLng.longitude + 0.03)
            )
            val intent = Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY,
                listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)
            )
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .setLocationBias(bounds)
                .build(context)
            placeLauncher.launch(intent)
        } else {
            Toast.makeText(context, "No se pudo obtener la ubicación actual.", Toast.LENGTH_SHORT).show()
        }
    }.addOnFailureListener {
        Toast.makeText(context, "Error al obtener ubicación: ${it.message}", Toast.LENGTH_SHORT).show()
    }
}
