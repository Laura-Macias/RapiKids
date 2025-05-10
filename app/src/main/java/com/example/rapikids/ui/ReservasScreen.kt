package com.example.rapikids.ui.screens

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.Calendar
import android.widget.Toast

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
    var entretenimientoSeleccionado by remember { mutableStateOf(false) }
    var educacionSeleccionado by remember { mutableStateOf(false) }
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
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val fields = listOf(
                        Place.Field.ID,
                        Place.Field.NAME,
                        Place.Field.ADDRESS,
                        Place.Field.LAT_LNG
                    )
                    val bounds = RectangularBounds.newInstance(
                        LatLng(location.latitude - 0.05, location.longitude - 0.05),
                        LatLng(location.latitude + 0.05, location.longitude + 0.05)
                    )
                    val intent = Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields
                    )
                        .setTypeFilter(TypeFilter.ESTABLISHMENT)
                        .setLocationBias(bounds)
                        .setCountries(listOf("CO"))
                        .setHint("Busca jardines o guarderías cercanas")
                        .build(context)
                    placeLauncher.launch(intent)
                } else {
                    Toast.makeText(context, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                }
            }
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
                "entretenimientoSeleccionado" to entretenimientoSeleccionado,
                "educacionSeleccionado" to educacionSeleccionado,
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
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

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = entretenimientoSeleccionado,
                onCheckedChange = {
                    entretenimientoSeleccionado = it
                    if (it) educacionSeleccionado = false
                }
            )
            Column {
                Text("Entretenimiento")
                Text("Aulas lúdicas para diversión", style = MaterialTheme.typography.bodySmall)
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
                Text("Educación")
                Text("Aulas de repaso y aprendizaje", style = MaterialTheme.typography.bodySmall)
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
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            if (location != null) {
                                val fields = listOf(
                                    Place.Field.ID,
                                    Place.Field.NAME,
                                    Place.Field.ADDRESS,
                                    Place.Field.LAT_LNG
                                )
                                val bounds = RectangularBounds.newInstance(
                                    LatLng(location.latitude - 0.05, location.longitude - 0.05),
                                    LatLng(location.latitude + 0.05, location.longitude + 0.05)
                                )
                                val intent = Autocomplete.IntentBuilder(
                                    AutocompleteActivityMode.FULLSCREEN, fields
                                )
                                    .setTypeFilter(TypeFilter.ESTABLISHMENT)
                                    .setLocationBias(bounds)
                                    .setCountries(listOf("CO"))
                                    .setHint("Busca jardines o guarderías cercanas")
                                    .build(context)
                                placeLauncher.launch(intent)
                            } else {
                                Toast.makeText(context, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                            }
                        }
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