package com.example.womensafetyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.womensafetyapp.data.ContactsRepository
import com.example.womensafetyapp.data.ContactsViewModel
import com.example.womensafetyapp.data.ContactsViewModelFactory
import com.example.womensafetyapp.ui.main.MainScreen
import com.example.womensafetyapp.ui.theme.WomenSafetyAppTheme

class MainActivity : ComponentActivity() {
    // Create a single instance of ContactsRepository for the entire app
    private val contactsRepository = ContactsRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Allows content to draw behind system bars

        setContent {
            WomenSafetyAppTheme {
                // Create the ViewModel with the repository
                val contactsViewModel: ContactsViewModel = viewModel(
                    factory = ContactsViewModelFactory(contactsRepository)
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        contactsViewModel = contactsViewModel
                    ) // 👈 This is your SafeGuard UI with top bar + navigation
                }
            }
        }
    }
}
