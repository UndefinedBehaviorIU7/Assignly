package com.example.assignly.presentation.taskList

import com.example.assignly.api.models.TasksList

sealed class TaskUiState {
    object Idle : TaskUiState()
    object Loading : TaskUiState()

    data class Success(val tasks: List<TasksList>) : TaskUiState()
    data class Error(val message: String) : TaskUiState()
}