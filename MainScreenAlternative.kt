// Alternative MainScreen implementation for better rendering
// Replace your MainScreen.kt content with this if the first solution doesn't work

package com.example.womensafetyapp.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier,
        topBar = { TopHeader() },
        containerColor = Color(0xFFF8F8F8),
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFF8F8F8))
            ) {
                NavigationTabs(
                    selectedTab = selectedTab,
                    onTabSelected = { newTab -> 
                        selectedTab = newTab
                    }
                )

                // Use AnimatedContent for smooth transitions
                androidx.compose.animation.AnimatedContent(
                    targetState = selectedTab,
                    label = "screen_transition",
                    modifier = Modifier.fillMaxSize()
                ) { targetTab ->
                    when (targetTab) {
                        0 -> EmergencyScreen()
                        1 -> ContactsScreen(contactsViewModel = contactsViewModel)
                        2 -> FeaturesScreen()
                        3 -> TipsScreen()
                        4 -> ProfileScreen()
                        else -> EmergencyScreen() // Default fallback
                    }
                }
            }
        }
    )
}

// Alternative approach using Composable functions
@Composable
private fun ScreenContent(
    selectedTab: Int,
    contactsViewModel: ContactsViewModel?
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (selectedTab) {
            0 -> {
                LaunchedEffect(selectedTab) {
                    // Ensure proper initialization
                }
                EmergencyScreen()
            }
            1 -> ContactsScreen(contactsViewModel = contactsViewModel)
            2 -> FeaturesScreen()
            3 -> TipsScreen()
            4 -> ProfileScreen()
            else -> EmergencyScreen()
        }
    }
}

