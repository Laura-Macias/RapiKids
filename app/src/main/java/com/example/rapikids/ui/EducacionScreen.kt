package com.example.rapikids.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rapikids.R
import com.example.rapikids.ui.Screen
import com.example.rapikids.ui.components.AutoPhotoCarousel

@Composable
fun EducacionScreen(navController: NavController, padding: PaddingValues) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_educacion_icono),
                contentDescription = "Icono Educación",
                modifier = Modifier.size(53.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Educación",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFEE5A24)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        AutoPhotoCarousel()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Descripción",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF79F1F)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "En el espacio de educación los niños están en aulas educativas en las cuales se repasan temas aprendidos y se enseñan nuevos temas según sus edades.",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Valor por hora :  $20.000",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))


        Button(
            onClick = { navController.navigate(Screen.Home.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5758BB)),
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Text(
                text = "Entendido",
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.group1),
            contentDescription = "Pie de página Educación",
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        )
    }
}