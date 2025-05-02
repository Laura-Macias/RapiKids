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
import com.example.rapikids.theme.RapiKidsTheme

@Composable
fun GuardadoScreen(padding: PaddingValues, navController: NavController) {
    var showPagarDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Estas son tus reservas guardadas",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )


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
                        text = "7 de Mayo 2025",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(Icons.Filled.Edit, contentDescription = "Editar")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Hora: 9:00 am - 10:00 am")
                Text(text = "Guardería: Niños felices")
                Text(text = "Crr. 18 # 45b -09")
                Text(text = "Servicio: Educación")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favorito")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Ya se realizó el pago",
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                }
            }
        }


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
                        text = "12 de Julio 2025",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(Icons.Filled.Edit, contentDescription = "Editar")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Hora: 9:00 am - 10:00 am")
                Text(text = "Guardería: Niños felices")
                Text(text = "Crr. 18 # 45b -09")
                Text(text = "Servicio: Educación")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favorito")
                        Spacer(modifier = Modifier.width(4.dp))

                    }
                    Button(onClick = { showPagarDialog = true }) {
                        Text(text = "Pagar")
                    }
                }
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
                text = "Las reservas que aun no se han pagado,\nno se tendrán en cuenta",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
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
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ok")
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

@Preview(showBackground = true)
@Composable
fun GuardadoScreenPreview() {
    RapiKidsTheme {
        GuardadoScreen(padding = PaddingValues(16.dp), navController = rememberNavControllerForPreview())
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