package com.example.womensafetyapp.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class EmergencyNumber(
    val service: String,
    val number: String,
    val description: String = ""
)

@Preview(showBackground = true)
@Composable
fun TipsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Section
        TipsHeader()
        
        // General Safety Section
        SafetyTipsCard(
            title = "General Safety",
            icon = Icons.Default.Shield,
            iconColor = Color(0xFF2196F3),
            backgroundColor = Color(0xFFE3F2FD),
            tips = listOf(
                "Trust your instincts - if something feels wrong, it probably is",
                "Stay alert and aware of your surroundings at all times",
                "Keep your phone charged and with you",
                "Share your location with trusted friends when going out",
                "Learn and practice basic self-defense techniques"
            )
        )
        
        // Home Safety Section
        SafetyTipsCard(
            title = "Home Safety",
            icon = Icons.Default.Home,
            iconColor = Color(0xFF4CAF50),
            backgroundColor = Color(0xFFE8F5E8),
            tips = listOf(
                "Always lock your doors and windows",
                "Install good lighting around your home entrance",
                "Don't open the door to strangers",
                "Consider a doorbell camera or security system",
                "Have emergency numbers easily accessible"
            )
        )
        
        // Transportation Section
        SafetyTipsCard(
            title = "Transportation",
            icon = Icons.Default.DirectionsCar,
            iconColor = Color(0xFF9C27B0),
            backgroundColor = Color(0xFFF3E5F5),
            tips = listOf(
                "Share your ride details with someone you trust",
                "Sit behind the driver in rideshares",
                "Check the license plate and driver before getting in",
                "Keep car doors locked while driving",
                "Park in well-lit, populated areas"
            )
        )
        
        // Social Situations Section
        SafetyTipsCard(
            title = "Social Situations",
            icon = Icons.Default.People,
            iconColor = Color(0xFFFF9800),
            backgroundColor = Color(0xFFFFF3E0),
            tips = listOf(
                "Stay with trusted friends, use the buddy system",
                "Watch your drink and never leave it unattended",
                "Let someone know where you're going and when to expect you back",
                "Have a safe word with friends for emergencies",
                "Keep some cash for emergency transportation"
            )
        )
        
        // Digital Safety Section
        SafetyTipsCard(
            title = "Digital Safety",
            icon = Icons.Default.Security,
            iconColor = Color(0xFFE91E63),
            backgroundColor = Color(0xFFFFEBEE),
            tips = listOf(
                "Be cautious about sharing personal information online",
                "Use privacy settings on social media platforms",
                "Don't share your real-time location publicly",
                "Be wary of meeting people you've only met online",
                "Keep your software and apps updated"
            )
        )
        
        // Emergency Numbers Section
        EmergencyNumbersCard()
        
        // Bottom spacing
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun TipsHeader() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Safety Tips & Resources",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun SafetyTipsCard(
    title: String,
    icon: ImageVector,
    iconColor: Color,
    backgroundColor: Color,
    tips: List<String>
) {
    var isExpanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(backgroundColor, shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = Color.Gray
                )
            }
            
            if (isExpanded) {
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tips.forEach { tip ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(iconColor, shape = CircleShape)
                                    .padding(top = 6.dp)
                            )
                            Text(
                                text = tip,
                                fontSize = 14.sp,
                                color = Color.Black,
                                lineHeight = 20.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmergencyNumbersCard() {
    val emergencyNumbers = listOf(
        EmergencyNumber("Emergency Services", "911"),
        EmergencyNumber("National Domestic Violence", "1-800-799-7233"),
        EmergencyNumber("Crisis Text Line", "Text HOME to 741741"),
        EmergencyNumber("RAINN Hotline", "1-800-656-4673")
    )
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFFFEBEE), shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Emergency",
                        tint = Color(0xFFE91E63),
                        modifier = Modifier.size(20.dp)
                    )
                }
                
                Text(
                    text = "Emergency Numbers",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
            
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                emergencyNumbers.forEach { emergency ->
                    EmergencyNumberRow(emergency)
                }
            }
        }
    }
}

@Composable
fun EmergencyNumberRow(emergency: EmergencyNumber) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = emergency.service,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        
        Text(
            text = emergency.number,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF2196F3)
        )
    }
}
