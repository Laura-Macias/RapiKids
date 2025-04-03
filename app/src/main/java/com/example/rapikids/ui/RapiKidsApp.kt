package com.example.rapikids

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.rapikids.ui.RapiKidsNavHost
import com.example.rapikids.theme.RapiKidsTheme

class RapiKidsApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RapiKidsTheme {
                RapiKidsNavHost()
            }
        }
    }
}
