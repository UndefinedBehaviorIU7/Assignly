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
    private val _uiState = MutableStateFlow<AddTaskUIState>(AddTaskUIState.Idle())
    val uiState = _uiState.asStateFlow()

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
                    deadline = current.deadline,
                    status = current.status,
                    members = current.members,
                )
            }

            else -> Unit
        }
    }


    fun membersChange(newMembers: List<Int>) {
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
                    deadline = current.deadline,
                    status = current.status,
                    members = current.members,
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
                    deadline = current.deadline,
                    status = current.status,
                    members = current.members,
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
                    deadline = current.deadline,
                    status = current.status,
                    members = current.members,
                )
            }

            else -> Unit
        }
    }

    fun deadlineChange(newDeadline: String) {
        when (val current = _uiState.value) {
            is AddTaskUIState.Idle -> {
                _uiState.value = current.copy(deadline = newDeadline)
            }

            is AddTaskUIState.Error -> {
                _uiState.value = AddTaskUIState.Idle(
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadline = current.deadline,
                    status = current.status,
                    members = current.members,
                )
            }

            else -> Unit
        }
    }

    fun addtask() {
        val current = _uiState.value

        if (current is AddTaskUIState.Idle) {


            _uiState.value = AddTaskUIState.Loading(
                groupId = current.groupId,
                ownerId = current.ownerId,
                name = current.name,
                description = current.description,
                summary = current.summary,
                deadline = current.deadline,
                status = current.status,
                members = current.members,
            )

            viewModelScope.launch {
                try {
                    val request = NetworkService.api.addTask(
                        groupId = current.groupId,
                        ownerId = current.ownerId,
                        name = current.name,
                        description = current.description,
                        summary = current.summary,
                        deadline = current.deadline,
                        status = current.status,
                        members = current.members
                    )
                } catch (e: HttpException) {

                }
            }
        }
    }

}