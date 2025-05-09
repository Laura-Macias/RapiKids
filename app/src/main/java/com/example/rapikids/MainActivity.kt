package com.example.rapikids

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.rapikids.ui.RapiKidsNavHost
import com.example.rapikids.theme.RapiKidsTheme
import com.google.android.libraries.places.api.Places
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa Google Places si aún no ha sido inicializado
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyAh_zUP_Ha83nnAkHqeZFat-qtCejtCVcI")
        }

        setContent {
            RapiKidsTheme {
                RapiKidsNavHost()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RapiKidsTheme {
        val navController = rememberNavController()
        RapiKidsNavHost()
    }
}
