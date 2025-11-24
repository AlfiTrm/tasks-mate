package com.projectpam.taskmate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectpam.taskmate.data.model.Task
import com.projectpam.taskmate.ui.theme.*

@Composable
fun TaskItemCard(
    task: Task,
    onCheckedChange: (Boolean) -> Unit,
    onEditClick: (Task) -> Unit = {},
    onDeleteClick: (Task) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }

    // Dynamic Background Color based on Category
    val cardBackgroundColor = when (task.category) {
        "Self Development" -> CategorySelfDevBg
        "Coolyeah" -> CategoryCoolyeahBg
        "Daily" -> CategoryDailyBg
        "Vibing" -> CategoryVibingBg
        else -> Color.White
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // Flat look as per design
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // ROW 1: Checkbox, Title, Time, More Icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = onCheckedChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Gray,
                        uncheckedColor = Color.Gray,
                        checkmarkColor = Color.White
                    ),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                task.dueTime?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box {
                    IconButton(
                        onClick = { showMenu = true },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = "More",
                            tint = Color.LightGray
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            onClick = {
                                showMenu = false
                                onEditClick(task)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Hapus") },
                            onClick = {
                                showMenu = false
                                onDeleteClick(task)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ROW 2: Priority Tag
            task.priority?.let { priority ->
                val (tagBg, tagText) = when (priority) {
                    "Urgent" -> PriorityUrgentBg to PriorityUrgentText
                    "Mid" -> PriorityMidBg to Color.White
                    "Chill" -> PriorityChillBg to Color.White
                    else -> Color.Gray to Color.White
                }

                Box(
                    modifier = Modifier
                        .background(tagBg, MaterialTheme.shapes.small)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = priority, // "Tinggi" in design but "Urgent" in data, mapping if needed? User said "Urgent" in data.
                        color = tagText,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ROW 3: Description
            task.description?.let { desc ->
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black.copy(alpha = 0.6f),
                    lineHeight = 16.sp
                )
            }
        }
    }
}
