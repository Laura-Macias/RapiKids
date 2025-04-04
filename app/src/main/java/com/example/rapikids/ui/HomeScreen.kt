package com.example.rapikids.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rapikids.R
import com.example.rapikids.ui.Screen
import com.example.rapikids.ui.components.AutoPhotoCarousel


@Composable
fun HomeScreen(navController: NavController, padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(24.dp))

        AutoPhotoCarousel()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "Calendario",
                    modifier = Modifier
                        .size(70.dp)
                        .weight(1f),
                    tint = androidx.compose.ui.graphics.Color.Unspecified
                )


                Button(
                    onClick = {navController.navigate(Screen.Reservas.route) },
                    modifier = Modifier
                        .width(300.dp)
                        .height(60.dp)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = androidx.compose.ui.graphics.Color(
                            0xFF5758BB
                        )
                    )
                ) {
                    Text(
                        text = "Realizar Reserva",
                        fontSize = 20.sp,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¿Qué quieres hacer hoy?",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 25.sp
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_question),
                contentDescription = "Ayuda",
                modifier = Modifier
                    .size(28.dp),
                tint = androidx.compose.ui.graphics.Color.Unspecified

            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {  },
                modifier = Modifier
                    .height(60.dp)
                    .width(200.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color(0xFF5758BB)
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Entretenimiento",
                    fontSize = 18.sp,
                    color = androidx.compose.ui.graphics.Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_entertainment),
                contentDescription = "Imagen Entretenimiento",
                modifier = Modifier.size(150.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_education),
                contentDescription = "Imagen Educación",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {  },
                modifier = Modifier
                    .height(60.dp)
                    .width(180.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color(0xFF5758BB)
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Educación",
                    fontSize = 18.sp,
                    color = androidx.compose.ui.graphics.Color.White
                )
            }
        }
    }
}








