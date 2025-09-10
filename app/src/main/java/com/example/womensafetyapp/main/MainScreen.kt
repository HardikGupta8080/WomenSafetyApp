package com.example.womensafetyapp.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.womensafetyapp.Screen.ContactsScreen
import com.example.womensafetyapp.Screen.FeaturesScreen
import com.example.womensafetyapp.Screen.ProfileScreen
import com.example.womensafetyapp.Screen.TipsScreen
import com.example.womensafetyapp.main.EmergencyScreen
import com.example.womensafetyapp.main.NavigationTabs
import com.example.womensafetyapp.main.TopHeader
import com.example.womensafetyapp.data.ContactsViewModel

@Preview(showBackground = true)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    contactsViewModel: ContactsViewModel? = null
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        modifier = modifier,
        topBar = { TopHeader() },
        containerColor = Color(0xFFF8F8F8), // Off-white background
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFF8F8F8)) // Off-white background for content area
            ) {
                NavigationTabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it })

                when (selectedTab) {
                    0 -> EmergencyScreen()
                    1 -> ContactsScreen(contactsViewModel = contactsViewModel)
                    2 -> FeaturesScreen()
                    3 -> TipsScreen()
                    4 -> ProfileScreen()
                }
            }
        }
    )
}
