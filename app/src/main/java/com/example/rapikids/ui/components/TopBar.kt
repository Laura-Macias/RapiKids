package com.example.rapikids.ui.components

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
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, onMenuClick: () -> Unit, userName: String) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (userName.isNotEmpty()) userName else "RapiKids",
                    fontSize = if (userName.isNotEmpty()) 20.sp else 28.sp,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = "Menú",
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFD980FA)
        )
    )
}