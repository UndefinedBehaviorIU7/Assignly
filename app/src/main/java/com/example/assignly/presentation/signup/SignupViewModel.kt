package com.example.assignly.presentation.signup

import android.app.Application
import android.health.connect.datatypes.HeartRateRecord
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignly.api.NetworkService
import com.example.assignly.presentation.login.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed class SignupUiState {
    data class Idle (
        val login: String = "",
        val tag: String = "",
        val password: String = "",
        val passwordRepeat: String = ""
    ): SignupUiState()

    data class Loading (
        val login: String,
        val tag: String,
        val password: String,
        val passwordRepeat: String
    ): SignupUiState()

    data class Error (
        val login: String,
        val tag: String,
        val password: String,
        val passwordRepeat: String,
        val errorMessage: String
    ): SignupUiState()

    data class Success (
        val successMessage: String
    ): SignupUiState()
}

class SignupViewModel(application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<SignupUiState>(SignupUiState.Idle())
    val uiState = _uiState.asStateFlow()

    fun loginChange(newLogin: String) {
        when (val current = _uiState.value) {
            is SignupUiState.Idle -> {
                _uiState.value = current.copy(login = newLogin)
            }

            is SignupUiState.Error -> {
                _uiState.value = SignupUiState.Idle(
                    login = current.login,
                    tag = current.tag,
                    password = current.password,
                    passwordRepeat = current.passwordRepeat
                )
            }

            else -> Unit
        }
    }

    fun tagChange(newTag: String) {
        when (val current = _uiState.value) {
            is SignupUiState.Idle -> {
                _uiState.value = current.copy(password = newTag)
            }

            is SignupUiState.Error -> {
                _uiState.value = SignupUiState.Idle(
                    login = current.login,
                    tag = current.tag,
                    password = current.password,
                    passwordRepeat = current.passwordRepeat
                )
            }

            else -> Unit
        }
    }

    fun passwordChange(newPassword: String) {
        when (val current = _uiState.value) {
            is SignupUiState.Idle -> {
                _uiState.value = current.copy(password = newPassword)
            }

            is SignupUiState.Error -> {
                _uiState.value = SignupUiState.Idle(
                    login = current.login,
                    tag = current.tag,
                    password = current.password,
                    passwordRepeat = current.passwordRepeat
                )
            }

            else -> Unit
        }
    }

    fun passwordRepeatChange(newPasswordRepeat: String) {
        when (val current = _uiState.value) {
            is SignupUiState.Idle -> {
                _uiState.value = current.copy(password = newPasswordRepeat)
            }

            is SignupUiState.Error -> {
                _uiState.value = SignupUiState.Idle(
                    login = current.login,
                    tag = current.tag,
                    password = current.password,
                    passwordRepeat = current.passwordRepeat
                )
            }

            else -> Unit
        }
    }

    fun signup() {
        val current = _uiState.value

        if (current is SignupUiState.Idle) {
            if (current.login.isBlank() || current.password.isBlank()) {
                _uiState.value = SignupUiState.Error(
                    login = current.login,
                    tag = current.tag,
                    password = current.password,
                    passwordRepeat = current.passwordRepeat,
                    errorMessage = "Fields shouldn't be blank"
                )
            }

            _uiState.value = SignupUiState.Loading(
                login = current.login,
                tag = current.tag,
                password = current.password,
                passwordRepeat = current.passwordRepeat
            )

            viewModelScope.launch {
                try {
                    if (current.password != current.passwordRepeat)  {
                        _uiState.value = SignupUiState.Error (
                            login = current.login,
                            tag = current.tag,
                            password = current.password,
                            passwordRepeat = current.passwordRepeat,
                            errorMessage = "passwords don't match"
                        )
                    } else {
                        val request = NetworkService.signup.signup(
                            login = current.login,
                            tag = current.tag,
                            password = current.password,
                            image = "" // TODO: дописать загрузку изображений
                        )
                    }
                } catch (e: HttpException) {
                    if (e.code() == 409) {
                        _uiState.value = SignupUiState.Error (
                            login = current.login,
                            tag = current.tag,
                            password = current.password,
                            passwordRepeat = current.passwordRepeat,
                            errorMessage = "user already exists"
                        )
                    }
                }
            }
        }
    }
}