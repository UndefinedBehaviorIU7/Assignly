package com.example.assignly.presentation.taskInfo

import android.app.Application
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.assignly.api.NetworkService
import com.example.assignly.api.models.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskInfoViewModel(application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(TaskInfoUiState.Idle(Task()))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val request = NetworkService.api.taskById()

            } catch (e: HttpException) {
            }
        }
    }
}