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

    fun loadTasks() {
        uiState = uiState.copy(isLoading = true, errorMessage = null)

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
    }

    fun addTask(
        title: String,
        description: String?,
        category: String?,
        dueDate: Timestamp?,
        dueTime: String?
    ) {
        uiState = uiState.copy(isLoading = true, errorMessage = null)

        taskRepository.addTask(title, description, category, dueDate, dueTime) { success, error ->
            uiState = uiState.copy(
                isLoading = false,
                errorMessage = error
            )
            if (success) {
                loadTasks()
            }
        }
    }

    fun setCompleted(taskId: String, completed: Boolean) {
        taskRepository.setTaskCompleted(taskId, completed) { success, error ->
            if (!success) {
                uiState = uiState.copy(errorMessage = error)
            } else {
                loadTasks()
            }
        }
    }
}
