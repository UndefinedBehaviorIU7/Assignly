package com.example.assignly.presentation.addtask

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignly.api.NetworkService
import com.example.assignly.presentation.addtask.AddTaskUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.ByteArrayOutputStream

class AddTaskViewModel(application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<AddTaskUIState>(AddTaskUIState.AddTask())
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
                    passwordRepeat = current.passwordRepeat,
                    image = current.image
                )
            }

            else -> Unit
        }
    }

    fun tagChange(newTag: String) {
        when (val current = _uiState.value) {
            is SignupUiState.Idle -> {
                _uiState.value = current.copy(tag = newTag)
            }

            is SignupUiState.Error -> {
                _uiState.value = SignupUiState.Idle(
                    login = current.login,
                    tag = current.tag,
                    password = current.password,
                    passwordRepeat = current.passwordRepeat,
                    image = current.image
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
                    passwordRepeat = current.passwordRepeat,
                    image = current.image
                )
            }

            else -> Unit
        }
    }

    fun passwordRepeatChange(newPasswordRepeat: String) {
        when (val current = _uiState.value) {
            is SignupUiState.Idle -> {
                _uiState.value = current.copy(passwordRepeat = newPasswordRepeat)
            }

            is SignupUiState.Error -> {
                _uiState.value = SignupUiState.Idle(
                    login = current.login,
                    tag = current.tag,
                    password = current.password,
                    passwordRepeat = current.passwordRepeat,
                    image = current.image
                )
            }

            else -> Unit
        }
    }

    fun imageChange(newImage: Uri?) {
        when (val current = _uiState.value) {
            is SignupUiState.Idle -> {
                _uiState.value = current.copy(image = newImage)
            }

            is SignupUiState.Error -> {
                _uiState.value = SignupUiState.Idle(
                    login = current.login,
                    tag = current.tag,
                    password = current.password,
                    passwordRepeat = current.passwordRepeat,
                    image = current.image
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
                    image = current.image,
                    errorMessage = "Fields shouldn't be blank"
                )
                return
            }

            _uiState.value = SignupUiState.Loading(
                login = current.login,
                tag = current.tag,
                password = current.password,
                passwordRepeat = current.passwordRepeat,
                image = current.image
            )

            viewModelScope.launch {
                try {
                    if (current.password != current.passwordRepeat)  {
                        _uiState.value = SignupUiState.Error (
                            login = current.login,
                            tag = current.tag,
                            password = current.password,
                            passwordRepeat = current.passwordRepeat,
                            image = current.image,
                            errorMessage = "passwords don't match"
                        )
                    } else {
                        val request = NetworkService.api.signup(
                            login = current.login,
                            tag = current.tag,
                            password = current.password,
                            image = imageConvert(current.image)
                        )

                        _uiState.value = SignupUiState.Auth (
                            login = current.login,
                            password = current.password
                        )
                        auth()
                    }
                } catch (e: HttpException) {
                    if (e.code() == 409) {
                        _uiState.value = SignupUiState.Error (
                            login = current.login,
                            tag = current.tag,
                            password = current.password,
                            passwordRepeat = current.passwordRepeat,
                            image = current.image,
                            errorMessage = "user already exists"
                        )
                    } else if (e.code() == 404) {
                        _uiState.value = SignupUiState.Error (
                            login = current.login,
                            tag = current.tag,
                            password = current.password,
                            passwordRepeat = current.passwordRepeat,
                            image = current.image,
                            errorMessage = "could not add user"
                        )
                    }
                }
            }
        }
    }

    private fun auth() {
        val current = _uiState.value
        if (current is SignupUiState.Auth) {
            try {
                viewModelScope.launch {
                    val request = NetworkService.api.authenticate(
                        login = current.login,
                        password = current.password
                    )

                    val sharedPref = getApplication<Application>()
                        .getSharedPreferences("auth", Context.MODE_PRIVATE)
                    sharedPref.edit()
                        .putString("token", request.token)
                        .putInt("id", request.id)
                        .apply()

                    _uiState.value = SignupUiState.Success (
                        successMessage = "Signup success"
                    )
                }
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    _uiState.value = SignupUiState.Error (
                        login = "",
                        tag = "",
                        password = "",
                        passwordRepeat = "",
                        image = null,
                        errorMessage = "signup error"
                    )
                }
            }
        }
    }
}