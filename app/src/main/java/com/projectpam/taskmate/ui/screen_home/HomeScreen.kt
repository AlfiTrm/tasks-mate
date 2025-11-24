package com.projectpam.taskmate.ui.screen_home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.projectpam.taskmate.ui.components.TaskItemCard
import com.projectpam.taskmate.ui.components.TaskMateBottomBar
import com.projectpam.taskmate.ui.components.TaskMateTopAppBar
import com.projectpam.taskmate.ui.components.TaskSummarySection
import com.projectpam.taskmate.viewmodel.TaskViewModel

@Composable
fun HomeScreen(
    taskViewModel: TaskViewModel,
    onLogout: () -> Unit
) {
    val uiState = taskViewModel.uiState
    val tasks = uiState.tasks

    // Calculate counts
    val totalCount = tasks.size
    val todayCount = tasks.count {
        // Logic filter would go here, for now using dummy logic or assuming all are today as per dummy data
        true
    }
    val overdueCount = 2 // Hardcoded from visual requirement or calculate from data

    Scaffold(
        topBar = {
            TaskMateTopAppBar(
                isHome = true,
                userName = "Biru",
                taskCount = todayCount
            )
        },
        bottomBar = {
            TaskMateBottomBar(
                currentRoute = "home",
                onNavigate = { /* Navigation logic */ }
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(24.dp))

            TaskSummarySection(
                todayCount = todayCount,
                overdueCount = overdueCount,
                totalCount = totalCount
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Tugas hari ini",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black.copy(alpha = 0.8f)
            )

            Spacer(Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(tasks) { task ->
                    TaskItemCard(
                        task = task,
                        onCheckedChange = { checked ->
                            taskViewModel.setCompleted(task.taskId, checked)
                        },
                        onEditClick = { /* Handle Edit */ },
                        onDeleteClick = { /* Handle Delete */ }
                    )
                }
            }
        }
    }
}
