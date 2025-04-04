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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenServicioScreen(navController: NavController, padding: PaddingValues) {
    var showGuardarDialog by remember { mutableStateOf(false) }
    var showPagarDialog by remember { mutableStateOf(false) }

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
                        Image(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "Editar fecha",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "6 de Marzo",
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
                        Image(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "Editar hora",
                            modifier = Modifier.size(24.dp)
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
                                    text = "6:30 AM",
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
                                    text = "10:30 AM",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "TOTAL TIEMPO: 4 horas",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_metodo_pago),
                contentDescription = "Método de pago",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            var expanded by remember { mutableStateOf(false) }
            var selectedOptionText by remember { mutableStateOf("Método de pago") }
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
                        text = { Text("Tarjeta de crédito") },
                        onClick = {
                            selectedOptionText = "Tarjeta de crédito"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Efectivo") },
                        onClick = {
                            selectedOptionText = "Efectivo"
                            expanded = false
                        }
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
                    Text(
                        text = "Guardería Niños Felices",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Carrera 18 # 61-67",
                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                text = "$45.000",
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
                onClick = { showGuardarDialog = true },
                modifier = Modifier.weight(1f),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFF008080))
            ) {
                Text(text = "Guardar reserva", color = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { showPagarDialog = true },
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
    }
}