package com.projectpam.taskmate.data.model

import com.google.firebase.Timestamp

data class Task(
    val taskId: String = "",
    val uid: String = "",
    val title: String = "",
    val description: String? = null,
    val category: String? = null,
    val dueDate: Timestamp? = null,
    val dueTime: String? = null,
    val isCompleted: Boolean = false,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)
