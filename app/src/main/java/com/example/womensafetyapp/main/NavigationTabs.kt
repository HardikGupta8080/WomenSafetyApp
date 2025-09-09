package com.example.womensafetyapp.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.Text
import com.example.womensafetyapp.R

@Composable
fun NavigationTabs(
    selectedTab: Int = 0,
    onTabSelected: (Int) -> Unit = {}
) {
    val tabs = listOf(
        "Emergency" to R.drawable.alarm,
        "Contacts" to R.drawable.phonecall,
        "Features" to R.drawable.location,
        "Tips" to R.drawable.tips,
        "Profile" to R.drawable.woman
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F5FF))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        tabs.forEachIndexed { index, (label, resId) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.clickable { onTabSelected(index) }
            ) {
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = label,
                    modifier = Modifier.size(28.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = if (index == selectedTab) Color(0xFF8E2DE2) else Color.Gray
                )

                // Underline for selected tab
                Box(
                    modifier = Modifier
                        .size(width = 28.dp, height = 2.dp)
                        .background(
                            color = if (index == selectedTab) Color(0xFF8E2DE2) else Color.Transparent
                        )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationTabsPreview() {
    NavigationTabs()
}
