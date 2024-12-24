package com.example.assignly.presentation.taskList

import android.graphics.Bitmap
import com.example.assignly.api.models.Group
import com.example.assignly.api.models.Task

sealed class TaskUiState {
    object Idle : TaskUiState()
    object Loading : TaskUiState()

    data class All(
        val tasks: List<Task>,
        val group: Group
    ) : TaskUiState()

    data class InProcess(
        val tasks: List<Task>,
        val group: Group
    ) : TaskUiState()

    data class Done(
        val tasks: List<Task>,
        val group: Group
    ) : TaskUiState()

    data class Error(
        val message: String
    ) : TaskUiState()
}