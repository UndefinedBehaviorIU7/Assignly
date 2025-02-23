package com.example.assignly.presentation.signup

import android.net.Uri

sealed class SignupUiState {
    data class Idle (
        val login: String = "",
        val tag: String = "",
        val password: String = "",
        val passwordRepeat: String = "",
        val image: Uri? = null
    ): SignupUiState()

    data class Loading (
        val login: String,
        val tag: String,
        val password: String,
        val passwordRepeat: String,
        val image: Uri?
    ): SignupUiState()

    data class Error (
        val login: String,
        val tag: String,
        val password: String,
        val passwordRepeat: String,
        val image: Uri?,
        val errorMessage: String
    ): SignupUiState()

    data class Success (
        val successMessage: String
    ): SignupUiState()
}
