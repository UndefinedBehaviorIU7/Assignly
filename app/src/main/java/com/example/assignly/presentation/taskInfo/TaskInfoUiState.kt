package com.example.assignly.presentation.taskInfo

import com.example.assignly.api.models.Task

sealed class TaskInfoUiState {
    data class Idle (
        val task: Task
    ): TaskInfoUiState()

    object Loading: TaskInfoUiState()

    data class Error (
        val errorMessage: String
    ): TaskInfoUiState()
}