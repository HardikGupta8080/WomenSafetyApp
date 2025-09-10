package com.example.womensafetyapp.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun EmergencyScreen() {
    // Scrollable column to fit all content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // SOS Button (your previous EmergencyButton composable)
        EmergencyCard()

        // Emergency Response Cards
        EmergencyResourcesScreen()
    }
}


@Composable
fun EmergencyCard() {
    var isPressed by remember { mutableStateOf(false) }
    var countdown by remember { mutableStateOf(0) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title
            Text(
                text = "Emergency Response",
                color = Color.Red,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            // Subtitle
            Text(
                text = "Quick access to emergency services and contacts",
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            // SOS Button or Countdown UI
            if (isPressed) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(128.dp)
                            .background(Color.Red, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call, // or any call icon
                            contentDescription = "Calling",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = countdown.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                    }

                    Text(
                        text = "Calling emergency services in $countdown seconds",
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = {
                            isPressed = false
                            countdown = 0
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color.Red)
                    ) {
                        Text("Cancel", color = Color.Red)
                    }
                }
            } else {
                Button(
                    onClick = {
                        isPressed = true
                        countdown = 5
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = CircleShape,
                    modifier = Modifier.size(128.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Warning, // SOS icon
                            contentDescription = "SOS",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                        Text("SOS", color = Color.White)
                    }
                }

                Text(
                    text = "Press for emergency",
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }

            // Info box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFE5E5), shape = RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Red, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = "Emergency Mode Active",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "This app is for demonstration purposes. In a real emergency, call 911 immediately.",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }

    // Countdown logic
    LaunchedEffect(isPressed, countdown) {
        if (isPressed && countdown > 0) {
            kotlinx.coroutines.delay(1000)
            countdown--
            if (countdown == 0) {
                isPressed = false
                // Here you can trigger actual emergency logic
            }
        }
    }
}


@Composable
fun EmergencyResourcesScreen() {
    val emergencyTypes = listOf(
        EmergencyType("Medical Emergency", Icons.Default.Favorite, Color.Red, "Call for medical emergencies", "Call Ambulance"),
        EmergencyType("Fire Emergency", Icons.Default.LocalFireDepartment, Color(0xFFFFA500), "Fire department emergency line", "Call Fire Dept"),
        EmergencyType("Police Emergency", Icons.Default.Security, Color.Blue, "Police emergency response", "Call Police"),
        EmergencyType("Car Accident", Icons.Default.DirectionsCar, Color(0xFFFFD700), "Traffic accident or road emergency", "Call 911")
    )

    val quickInfo = listOf(
        QuickInfo("Current Location", Icons.Default.Place, "GPS coordinates will be shared with emergency services", "Share Location", Color(0xFF4CAF50)),
        QuickInfo("Medical Info", Icons.Default.MedicalServices, "Blood type, allergies, medications", "View Details", Color(0xFF2196F3)),
        QuickInfo("Emergency Contacts", Icons.Default.People, "3 contacts will be notified automatically", "View Contacts", Color(0xFF9C27B0))
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Quick Emergency Response
        Text("Quick Emergency Response", fontWeight = FontWeight.Bold, color = Color.Red, fontSize = 18.sp)
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(4.dp)
        ) {
            // Create rows with 2 items each
            emergencyTypes.chunked(2).forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowItems.forEach { item ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color.Gray),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .height(180.dp), // Fixed height for all cards
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(item.color, shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(item.icon, contentDescription = null, tint = Color.White)
                                }
                                Text(
                                    text = item.title, 
                                    fontWeight = FontWeight.Bold, 
                                    fontSize = 14.sp, 
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = item.description, 
                                    fontSize = 12.sp, 
                                    color = Color.Gray, 
                                    textAlign = TextAlign.Center,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Button(
                                    onClick = { /* TODO: Handle emergency call */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = item.buttonText, 
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                    // Add empty space if odd number of items in the last row
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        // Emergency Information
        Text("Emergency Information", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 16.sp)
        quickInfo.forEach { info ->
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(info.color.copy(alpha = 0.1f), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = info.icon, 
                                contentDescription = null, 
                                tint = info.color,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = info.title, 
                                fontWeight = FontWeight.SemiBold, 
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Text(
                                text = info.content, 
                                fontSize = 14.sp, 
                                color = Color.Gray,
                                lineHeight = 18.sp
                            )
                        }
                    }
                    Button(
                        onClick = { /* TODO: Action */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = info.color
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = info.action, 
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Emergency Tip
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFFFD580)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFFFA500))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Emergency Tip: In any emergency, prioritize your safety first. Get to a safe location before calling for help when possible.",
                    fontSize = 12.sp,
                    color = Color(0xFFFFA500)
                )
            }
        }
    }
}

data class EmergencyType(val title: String, val icon: ImageVector, val color: Color, val description: String, val buttonText: String)
data class QuickInfo(val title: String, val icon: ImageVector, val content: String, val action: String, val color: Color)



