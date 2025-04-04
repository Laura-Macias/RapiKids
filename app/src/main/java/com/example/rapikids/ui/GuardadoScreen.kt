package com.example.rapikids.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

        // Tarjeta de reserva 1 (Pagada)
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
                            color = Color(0xFF2E7D32), // Un tono de verde
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    // No hay botón de pagar aquí
                }
            }
        }

        // Tarjeta de reserva 2 (Pendiente de pago)
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
                        // No se indica el estado de pago aquí visualmente como texto
                    }
                    Button(onClick = { /* TODO: Implementar lógica de pagar */ }) {
                        Text(text = "Pagar")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Empuja el mensaje hacia abajo

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
    }
}
@Preview(showBackground = true)
@Composable
fun GuardadoScreenPreview() {
    RapiKidsTheme  {
        GuardadoScreen(
            padding = PaddingValues(0.dp),
            navController = TODO()
        )
    }
}
