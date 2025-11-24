package com.projectpam.taskmate.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskSummarySection(
    todayCount: Int,
    overdueCount: Int,
    totalCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp), // Height adjustment to match visual proportions
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Left Card: Tugas Hari Ini (Big)
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFDCE1FF) // Light Blueish from image
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Number
                Text(
                    text = "$todayCount",
                    fontSize = 80.sp, // Very large font as per design
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4B4AEF) // Darker Blue
                )

                // Label
                Column {
                    Text(
                        text = "Tugas",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black.copy(alpha = 0.5f)
                    )
                    Text(
                        text = "Hari Ini",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF4B4AEF)
                    )
                }
            }
        }

        // Right Column: Two stacked cards
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top Right: Terlewat
            SummarySmallCard(
                count = overdueCount,
                label = "Tugas\nTerlewat",
                backgroundColor = Color(0xFFFFDADA), // Pinkish
                textColor = Color(0xFFA84646), // Reddish text
                modifier = Modifier.weight(1f)
            )

            // Bottom Right: Semua
            SummarySmallCard(
                count = totalCount,
                label = "Semua\nTugas",
                backgroundColor = Color(0xFFD6F2F5), // Light Cyan/Greenish
                textColor = Color(0xFF2E7D85), // Darker Cyan text
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SummarySmallCard(
    count: Int,
    label: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "$count",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor.copy(alpha = 0.8f),
                    lineHeight = 14.sp
                )
            }

            // Arrow Circle
            Card(
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp),
                    tint = Color.Black.copy(alpha = 0.5f)
                )
            }
        }
    }
}
