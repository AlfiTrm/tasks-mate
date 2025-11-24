package com.projectpam.taskmate.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projectpam.taskmate.data.model.Task

class TaskRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private fun uid(): String? = auth.currentUser?.uid

    fun getTasksForUser(
        onResult: (Boolean, List<Task>?, String?) -> Unit
    ) {
        val userId = uid() ?: return onResult(false, null, "User not logged in")

        db.collection("tasks")
            .whereEqualTo("uid", userId)
            .get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.documents.mapNotNull { it.toObject(Task::class.java) }
                onResult(true, list, null)
            }
            .addOnFailureListener { e ->
                onResult(false, null, e.message)
            }
    }

    fun addTask(
        title: String,
        description: String?,
        category: String?,
        dueDate: Timestamp?,
        dueTime: String?,
        onResult: (Boolean, String?) -> Unit
    ) {
        val userId = uid() ?: return onResult(false, "User not logged in")

        val docRef = db.collection("tasks").document()
        val now = Timestamp.now()

        val task = Task(
            taskId = docRef.id,
            uid = userId,
            title = title,
            description = description,
            category = category,
            dueDate = dueDate,
            dueTime = dueTime,
            isCompleted = false,
            createdAt = now,
            updatedAt = now
        )

        docRef.set(task)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { e -> onResult(false, e.message) }
    }

    fun setTaskCompleted(
        taskId: String,
        completed: Boolean,
        onResult: (Boolean, String?) -> Unit
    ) {
        db.collection("tasks")
            .document(taskId)
            .update(
                mapOf(
                    "isCompleted" to completed,
                    "updatedAt" to Timestamp.now()
                )
            )
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { e -> onResult(false, e.message) }
    }
}
