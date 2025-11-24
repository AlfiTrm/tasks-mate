package com.projectpam.taskmate.ui.screen_home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.projectpam.taskmate.data.model.Task
import com.projectpam.taskmate.ui.components.TaskItemCard
import com.projectpam.taskmate.ui.components.TaskSummarySection
import com.projectpam.taskmate.viewmodel.TaskViewModel

@Composable
fun HomeScreen(
    taskViewModel: TaskViewModel,
    onLogout: () -> Unit
) {
    val uiState = taskViewModel.uiState
    val tasks = uiState.tasks

    // Contoh logic simple: hitung jumlah
    val totalCount = tasks.size
    val todayCount = totalCount // nanti bisa difilter by tanggal
    val overdueCount = 0        // nanti isi logic overdue

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        // greeting kayak tadi…

        Spacer(Modifier.height(16.dp))

        TaskSummarySection(
            todayCount = todayCount,
            overdueCount = overdueCount,
            totalCount = totalCount,
            onAddTaskClick = {
                // TODO: nav ke AddTaskScreen
            }
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Tugas hari ini",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(tasks) { task ->
                TaskItemCard(
                    task = task,
                    onCheckedChange = { checked ->
                        taskViewModel.setCompleted(task.taskId, checked)
                    }
                )
            }
        }

        // bottom nav nanti bisa nyusul
    }
}


@Composable
fun TaskCard(
    task: Task,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = onCheckedChange
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                task.dueTime?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}
