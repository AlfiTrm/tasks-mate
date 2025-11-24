package com.projectpam.taskmate.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.projectpam.taskmate.data.repository.AuthRepository


data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoggedIn: Boolean = false
)

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    var uiState by mutableStateOf(AuthUiState())
        private set

    fun register(name: String, email: String, password: String) {
        uiState = uiState.copy(isLoading = true, errorMessage = null)

        authRepository.signUp(name, email, password) { success, error ->
            uiState = if (success) {
                uiState.copy(
                    isLoading = false,
                    isLoggedIn = true,
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

    fun login(email: String, password: String) {
        uiState = uiState.copy(isLoading = true, errorMessage = null)

        authRepository.signIn(email, password) { success, error ->
            uiState = if (success) {
                uiState.copy(
                    isLoading = false,
                    isLoggedIn = true,
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

    fun logout() {
        authRepository.signOut()
        uiState = uiState.copy(isLoggedIn = false)
    }

    fun currentUid(): String? = authRepository.currentUserId()
}
