package com.projectpam.taskmate.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.projectpam.taskmate.data.model.Task
import com.projectpam.taskmate.data.repository.TaskRepository

data class TaskUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val tasks: List<Task> = emptyList()
)

class TaskViewModel(
    private val taskRepository: TaskRepository = TaskRepository()
) : ViewModel() {

    var uiState by mutableStateOf(TaskUiState())
        private set

    init {
        // Load some dummy data initially for UI testing since we might not have a backend connected
        // This is a temporary measure for development based on the user request context
        loadDummyTasks()
    }

    private fun loadDummyTasks() {
        val dummyTasks = listOf(
            Task(
                taskId = "1",
                title = "Cek Email",
                description = "Bersihkan kotak masuk dan prioritaskan balasan untuk email mendesak",
                category = "Self Development",
                priority = "Urgent",
                dueTime = "09:30",
                isCompleted = false
            ),
            Task(
                taskId = "2",
                title = "Selesaikan Metopen",
                description = "Tulis draft Bab 2 Metodologi Penelitian, minimal 500 kata",
                category = "Coolyeah",
                priority = "Urgent",
                dueTime = "15:00",
                isCompleted = false
            ),
            Task(
                taskId = "3",
                title = "Olahraga Ringan",
                description = "Lakukan peregangan atau jalan cepat selama 15 menit",
                category = "Daily",
                priority = "Mid",
                dueTime = "17:30",
                isCompleted = false
            ),
            Task(
                taskId = "4",
                title = "Siapkan Makan Malam",
                description = "Rencanakan dan mulai proses memasak untuk menu malam ini",
                category = "Vibing",
                priority = "Chill",
                dueTime = "19:00",
                isCompleted = false
            )
        )
        uiState = uiState.copy(tasks = dummyTasks)
    }

    fun loadTasks() {
        uiState = uiState.copy(isLoading = true, errorMessage = null)
        // For now, let's keep using dummy data to ensure UI matches the design requirements
        // In a real scenario, we would uncomment the repository call
        /*
        taskRepository.getTasksForUser { success, list, error ->
            uiState = if (success) {
                uiState.copy(
                    isLoading = false,
                    tasks = list ?: emptyList(),
                    errorMessage = null
                )
            } else {
                uiState.copy(
                    isLoading = false,
                    errorMessage = error
                )
            }
        }
        */
        loadDummyTasks()
        uiState = uiState.copy(isLoading = false)
    }

    fun addTask(
        title: String,
        description: String?,
        category: String?,
        dueDate: Timestamp?,
        dueTime: String?
    ) {
        // Mock implementation for UI demo
        val newTask = Task(
            taskId = System.currentTimeMillis().toString(),
            title = title,
            description = description,
            category = category,
            dueDate = dueDate,
            dueTime = dueTime
        )
        val currentTasks = uiState.tasks.toMutableList()
        currentTasks.add(newTask)
        uiState = uiState.copy(tasks = currentTasks)
    }

    fun setCompleted(taskId: String, completed: Boolean) {
         // Mock implementation
         val currentTasks = uiState.tasks.map {
             if (it.taskId == taskId) it.copy(isCompleted = completed) else it
         }
         uiState = uiState.copy(tasks = currentTasks)
    }
}
