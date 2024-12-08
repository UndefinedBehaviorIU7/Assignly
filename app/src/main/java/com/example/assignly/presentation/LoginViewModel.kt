package com.example.assignly.presentation

import android.health.connect.datatypes.HeartRateRecord
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignly.api.NetworkService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed class LoginUiState {
    data class Idle (
        val login: String = "",
        val password: String = ""
    ): LoginUiState()

    data class Loading (
        val login: String,
        val password: String
    ): LoginUiState()

    data class Error (
        val errorMessage: String
    ): LoginUiState()

    data class Success (
        val id: Int,
        val token: String,
        val successMessage: String
    ): LoginUiState()
}

class LoginViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle())

    fun usernameChange(newLogin: String) {
        when (val current = _uiState.value) {
            is LoginUiState.Idle -> {
                _uiState.value = current.copy(login = newLogin)
            }

            is LoginUiState.Error -> {
                _uiState.value = LoginUiState.Idle (
                    login = "",
                    password = ""
                )
            }

            else -> Unit
        }
    }

    fun passwordChange(newPassword: String) {
        when (val current = _uiState.value) {
            is LoginUiState.Idle -> {
                _uiState.value = current.copy(password = newPassword)
            }

            is LoginUiState.Error -> {
                _uiState.value = LoginUiState.Idle (
                    login = "",
                    password = "",
                )
            }

            else -> Unit
        }
    }

    fun login() {
        val current = _uiState.value

        if (current is LoginUiState.Idle) {
            if (current.login.isBlank() || current.password.isBlank()) {
                _uiState.value = LoginUiState.Error (
                    errorMessage = "Поля не должны быть пустыми"
                )
            }

            _uiState.value = LoginUiState.Loading (
                login= current.login,
                password = current.password
            )

            viewModelScope.launch {
                try {
                    val request = NetworkService.auth.authenticate(
                        login = current.login,
                        password = current.password
                    )
                    val id = request.id
                    val token = request.token

                    _uiState.value = LoginUiState.Success (
                        id = id,
                        token = token,
                        successMessage = "Вход успешно выполнен"
                    )
                } catch (e: HttpException) {
                    if (e.code() == 400) {
                        _uiState.value = LoginUiState.Error (
                            errorMessage = "Неверный пароль"
                        )
                    } else if (e.code() == 404) {
                        _uiState.value = LoginUiState.Error (
                            errorMessage = "Пользователь не найден"
                        )
                    } else {
                        _uiState.value = LoginUiState.Error (
                            errorMessage = "Ошибка аутентификации"
                        )
                    }
                }
            }
        }
    }
}
