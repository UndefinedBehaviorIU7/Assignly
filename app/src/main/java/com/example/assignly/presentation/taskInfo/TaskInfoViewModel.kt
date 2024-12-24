package com.example.assignly.presentation.taskInfo

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.assignly.api.NetworkService
import com.example.assignly.api.models.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskInfoViewModel(application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<TaskInfoUiState>(TaskInfoUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val sharedPref = getApplication<Application>()
        .getSharedPreferences("auth", Context.MODE_PRIVATE)
    val token = sharedPref.getString("token", "")

    val taskId = sharedPref.getInt("taskId", 0)
    private val id = taskId

    suspend fun fetchTasks(): Task? {
        try {
            val request = NetworkService.api.taskById(token = token.toString(), taskId = id)
            return request
        } catch (e: HttpException) {
            return null
        }
    }

    init {
        viewModelScope.launch {

            val task = fetchTasks()
            if (task != null) {
                _uiState.value = TaskInfoUiState.Idle(
                    task = task
                )
            } else {
                _uiState.value = TaskInfoUiState.Error(
                    errorMessage = "request error"
                )
            }
        }
    }

    fun retry() {
        viewModelScope.launch {
            val task = fetchTasks()
            if (task != null) {
                _uiState.value = TaskInfoUiState.Idle(
                    task = task
                )
            } else {
                _uiState.value = TaskInfoUiState.Error(
                    errorMessage = "request error"
                )
            }
        }
    }
}
