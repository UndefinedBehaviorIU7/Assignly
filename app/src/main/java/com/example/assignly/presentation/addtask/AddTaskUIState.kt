package com.example.assignly.presentation.addtask

import android.media.session.MediaSession.Token
import android.net.Uri
import com.example.assignly.api.models.User
import com.example.assignly.presentation.signup.SignupUiState
import retrofit2.http.Query
import androidx.compose.ui.geometry.Size

sealed class AddTaskUIState {

    data class Idle (
        val token: String = "",
        val groupId: Int = 0,
        val ownerId: Int = 0,
        val name: String = "",
        val description: String = "",
        val summary: String = "",
        val deadlinedata: String = "",
        val deadlinetime: String = "",
        val status: Int = 0,
        val members: MutableList<User> = mutableListOf(),
        var menuExpanded: Boolean = false,
        var membersFieldPosition: Size = Size.Zero,
        val allUsers: List<User> = emptyList<User>()
    ): AddTaskUIState()

    data class Error (
        val token: String,
        val groupId: Int,
        val ownerId: Int,
        val name: String,
        val description: String,
        val summary: String,
        val deadlinedata: String = "",
        val deadlinetime: String = "",
        val status: Int,
        val members: MutableList<User>,
        val errorMessage: String,
        var errorField: String,
        var menuExpanded: Boolean,
        var membersFieldPosition: Size,
        val allUsers: List<User>
    ): AddTaskUIState()

    data class Loading (
        val token: String,
        val groupId: Int,
        val ownerId: Int,
        val name: String,
        val description: String,
        val summary: String,
        val deadlinedata: String = "",
        val deadlinetime: String = "",
        val status: Int,
        val members: MutableList<User>,
        var menuExpanded: Boolean,
        var membersFieldPosition: Size,
        val allUsers: List<User>
    ): AddTaskUIState()

    data class Success (
        val text : String
    ): AddTaskUIState()

}
