package com.example.assignly.presentation.addtask

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignly.R
import com.example.assignly.api.NetworkService
import com.example.assignly.api.models.User
import com.example.assignly.presentation.addtask.AddTaskUIState
import com.example.assignly.presentation.signup.SignupUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.ByteArrayOutputStream


data class RequestResult (
    val code: Int,
    val result: List<User>
)

class AddTaskViewModel(application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<AddTaskUIState>(AddTaskUIState.Idle())
    val uiState = _uiState.asStateFlow()
    private suspend fun loadUsers(): RequestResult {
        try {
            val request = NetworkService.api.allUsers()
            return RequestResult(
                code = 0,
                result = request
            )
        } catch (e: Exception) {
            return RequestResult (
                code = 1,
                result = emptyList()
            )
        }
    }

    init {
        viewModelScope.launch {
            val result = loadUsers()
            _uiState.value = AddTaskUIState.Idle(
                allUsers = if (result.code == 0) result.result else emptyList(),
                ownerId = 1,
                groupId = 21,
            )
        }
    }
    fun updateUIState(
        newName: String? = null,
        newSummary: String? = null,
        newDescription: String? = null,
        newDeadlineData: String? = null,
        newDeadlineTime: String? = null,
        newMembers: MutableList<User>? = null,
        newStatus: Int? = null,
        newMenuExtended: Boolean? = null,
    ) {
        when (val current = _uiState.value) {
            is AddTaskUIState.Idle -> {
                _uiState.value = current.copy(
                    name = newName ?: current.name,
                    summary = newSummary ?: current.summary,
                    description = newDescription ?: current.description,
                    deadlinedata = newDeadlineData ?: current.deadlinedata,
                    deadlinetime = newDeadlineTime ?: current.deadlinetime,
                    members = newMembers ?: current.members,
                    status = newStatus ?: current.status,
                    menuExpanded = newMenuExtended ?: current.menuExpanded
                )
            }

            is AddTaskUIState.Error -> {
                _uiState.value = AddTaskUIState.Idle(
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }

    fun nameChange(newName: String) {
        when (val current = _uiState.value) {
            is AddTaskUIState.Idle -> {
                _uiState.value = current.copy(name = newName)
            }

            is AddTaskUIState.Error -> {
                _uiState.value = AddTaskUIState.Idle(
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }


    fun membersChange(newMembers: MutableList<User>) {
        when (val current = _uiState.value) {
            is AddTaskUIState.Idle -> {
                _uiState.value = current.copy(members = newMembers)
            }

            is AddTaskUIState.Error -> {
                _uiState.value = AddTaskUIState.Idle(
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }

    fun summaryChange(newSummary: String) {
        when (val current = _uiState.value) {
            is AddTaskUIState.Idle -> {
                _uiState.value = current.copy(summary = newSummary)
            }

            is AddTaskUIState.Error -> {
                _uiState.value = AddTaskUIState.Idle(
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }

    fun descriptionChange(newDescription: String) {
        when (val current = _uiState.value) {
            is AddTaskUIState.Idle -> {
                _uiState.value = current.copy(description = newDescription)
            }

            is AddTaskUIState.Error -> {
                _uiState.value = AddTaskUIState.Idle(
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }

    fun deadlinedataChange(newDeadlineData: String) {
        when (val current = _uiState.value) {
            is AddTaskUIState.Idle -> {
                _uiState.value = current.copy(deadlinedata = newDeadlineData)
            }

            is AddTaskUIState.Error -> {
                _uiState.value = AddTaskUIState.Idle(
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }

    fun deadlinetimeChange(newDeadlineTime: String) {
        when (val current = _uiState.value) {
            is AddTaskUIState.Idle -> {
                _uiState.value = current.copy(deadlinetime = newDeadlineTime)
            }

            is AddTaskUIState.Error -> {
                _uiState.value = AddTaskUIState.Idle(
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }


    fun addtask() {
        val current = _uiState.value

        if (current is AddTaskUIState.Idle) {

            if (current.name.isBlank())
            {
                Log.d("qwertygogo", "name: ${current.name}")
                _uiState.value = AddTaskUIState.Error(
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    errorMessage = "pixel",
                    errorField = "name",
                    membersFieldPosition = current.membersFieldPosition,
                    menuExpanded = current.menuExpanded,
                    allUsers = current.allUsers
                )
                return
            }

            if (current.description.isBlank())
            {
                _uiState.value = AddTaskUIState.Error(
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    errorMessage = "pixel",
                    errorField = "description",
                    membersFieldPosition = current.membersFieldPosition,
                    menuExpanded = current.menuExpanded,
                    allUsers = current.allUsers
                )
                return
            }

            if (current.summary.isBlank())
            {
                _uiState.value = AddTaskUIState.Error(
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    errorMessage = "pixel",
                    errorField = "summary",
                    membersFieldPosition = current.membersFieldPosition,
                    menuExpanded = current.menuExpanded,
                    allUsers = current.allUsers
                )
                return
            }


            _uiState.value = AddTaskUIState.Loading(
                groupId = current.groupId,
                ownerId = current.ownerId,
                name = current.name,
                description = current.description,
                summary = current.summary,
                deadlinedata = current.deadlinedata,
                deadlinetime = current.deadlinetime,
                status = current.status,
                members = current.members,
                membersFieldPosition = current.membersFieldPosition,
                menuExpanded = current.menuExpanded,
                allUsers = current.allUsers
            )

            viewModelScope.launch {
                try {
                    val request = NetworkService.api.addTask(
                        groupId = current.groupId,
                        ownerId = current.ownerId,
                        name = current.name,
                        description = current.description,
                        summary = current.summary,
                        deadline = current.deadlinedata, // исправить
                        status = current.status,
                        members = current.members
                    )
                    _uiState.value = AddTaskUIState.Success (
                        text = "ok"
                    )
                } catch (e: HttpException) {

                }
            }
        }
    }

    fun membersToString(members: List<User>): String {
        return members.joinToString(" ") { it.tag }
    }


}