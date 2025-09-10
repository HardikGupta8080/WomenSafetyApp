package com.example.womensafetyapp.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.womensafetyapp.data.ContactsViewModel
import com.example.womensafetyapp.data.EmergencyContact
import androidx.compose.runtime.collectAsState

@Preview(showBackground = true)
@Composable
fun ContactsScreen(contactsViewModel: ContactsViewModel? = null) {
    // Get contacts from ViewModel StateFlow or use empty list for preview
    val contacts by contactsViewModel?.contacts?.collectAsState() ?: remember {
        mutableStateOf(
            listOf(
                EmergencyContact(
                    name = "Mom",
                    phoneNumber = "+1 (555) 123-4567", 
                    relationship = "Mother"
                ),
                EmergencyContact(
                    name = "Best Friend Sarah",
                    phoneNumber = "+1 (555) 987-6543", 
                    relationship = "Friend"
                )
            )
        )
    }
    
    // Get loading and error states
    val isLoading by contactsViewModel?.isLoading?.collectAsState() ?: remember { mutableStateOf(false) }
    val error by contactsViewModel?.error?.collectAsState() ?: remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Section
        ContactsHeader()
        
        // Error Message
        error?.let { errorMessage ->
            ErrorCard(
                message = errorMessage,
                onDismiss = { contactsViewModel?.clearError() }
            )
        }
        
        // Add Contact Button
        AddContactButton(
            onAddContact = {
                // Add contact using ViewModel
                contactsViewModel?.addContact(
                    name = "New Contact",
                    phoneNumber = "+1 (555) 000-0000",
                    relationship = "Emergency"
                )
            },
            isLoading = isLoading
        )
        
        // Loading Indicator
        if (isLoading) {
            LoadingCard()
        }
        
        // Emergency Contacts List
        contacts.forEach { contact ->
            EmergencyContactCard(
                contact = contact,
                onCall = { 
                    // TODO: Implement call functionality
                },
                onDelete = { 
                    // Delete contact using ViewModel
                    contactsViewModel?.removeContact(contact)
                },
                isLoading = isLoading
            )
        }
        
        // Empty state
        if (!isLoading && contacts.isEmpty()) {
            EmptyStateCard()
        }
        
        // Bottom spacing
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ContactsHeader() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Emergency Contacts",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Manage your trusted contacts for emergency situations",
            fontSize = 14.sp,
            color = Color.Gray,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun AddContactButton(onAddContact: () -> Unit, isLoading: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Emergency Contacts",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        
        Button(
            onClick = onAddContact,
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Contact",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Add Contact",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun EmergencyContactCard(
    contact: EmergencyContact,
    onCall: () -> Unit,
    onDelete: () -> Unit,
    isLoading: Boolean = false
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Contact Info Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Profile Icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFFE0E0E0), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Contact",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Contact Details
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = contact.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = contact.phoneNumber,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    // Relationship Badge
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFF2196F3),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = contact.relationship,
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            // Action Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Call Button
                IconButton(
                    onClick = onCall,
                    enabled = !isLoading,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF4CAF50), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                
                // Delete Button
                IconButton(
                    onClick = onDelete,
                    enabled = !isLoading,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFFF5252), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorCard(message: String, onDismiss: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE57373))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Error",
                    tint = Color(0xFFE57373),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = message,
                    color = Color(0xFFD32F2F),
                    fontSize = 14.sp
                )
            }
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Dismiss",
                    tint = Color(0xFFE57373),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun LoadingCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Color(0xFF8E2DE2),
                strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Loading contacts...",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun EmptyStateCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.People,
                contentDescription = "No contacts",
                tint = Color.Gray,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = "No Emergency Contacts",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = "Add your trusted contacts to get started. They will be notified in case of emergency.",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}