package com.example.assignly.presentation.login

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignly.api.NetworkService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle())
    val uiState = _uiState.asStateFlow()

    fun loginChange(newLogin: String) {
        when (val current = _uiState.value) {
            is LoginUiState.Idle -> {
                _uiState.value = current.copy(login = newLogin)
            }

            is LoginUiState.Error -> {
                _uiState.value = LoginUiState.Idle(
                    login = current.login,
                    password = current.password
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
                _uiState.value = LoginUiState.Idle(
                    login = current.login,
                    password = current.password,
                )
            }

            else -> Unit
        }
    }

    fun auth() {
        val current = _uiState.value

        if (current is LoginUiState.Idle) {
            if (current.login.isBlank() || current.password.isBlank()) {
                _uiState.value = LoginUiState.Error(
                    login = current.login,
                    password = current.password,
                    errorMessage = "Fields shouldn't be blank"
                )
                return
            }

            _uiState.value = LoginUiState.Loading(
                login = current.login,
                password = current.password
            )

            viewModelScope.launch {
                try {
                    val request = NetworkService.api.authenticate(
                        login = current.login,
                        password = current.password
                    )
                    val id = request.id
                    val token = request.token

                    val sharedPref = getApplication<Application>()
                        .getSharedPreferences("auth", Context.MODE_PRIVATE)
                    sharedPref.edit()
                        .putString("token", token)
                        .putInt("id", id)
                        .apply()

                    _uiState.value = LoginUiState.Success(
                        id = id,
                        token = token,
                        successMessage = "Success"
                    )


                } catch (e: HttpException) {
                    if (e.code() == 400) {
                        _uiState.value = LoginUiState.Error(
                            login = current.login,
                            password = current.password,
                            errorMessage = "incorrect password"
                        )
                    } else if (e.code() == 404) {
                        _uiState.value = LoginUiState.Error(
                            login = current.login,
                            password = current.password,
                            errorMessage = "user not found"
                        )
                    } else {
                        _uiState.value = LoginUiState.Error(
                            login = current.login,
                            password = current.password,
                            errorMessage = "authorisation error"
                        )
                    }
                } catch (e: Exception) {
                    _uiState.value = LoginUiState.Error(
                        login = current.login,
                        password = current.password,
                        errorMessage = "no internet connection"
                    )
                }
            }
        }
    }
}
