package com.example.womensafetyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.womensafetyapp.ui.main.MainScreen
import com.example.womensafetyapp.ui.theme.WomenSafetyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Allows content to draw behind system bars

        setContent {
            WomenSafetyAppTheme {
                MainScreen() // 👈 This is your SafeGuard UI with top bar + navigation
            }
        }
    }
}
