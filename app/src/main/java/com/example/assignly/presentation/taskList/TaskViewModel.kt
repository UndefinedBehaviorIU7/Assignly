package com.example.assignly.presentation.taskList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignly.api.NetworkService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<TaskUiState>(TaskUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val repository = TaskRepository(NetworkService.api)
    fun fetchTasks(token: String, groupId: Int, limit: Int, offset: Int) {
        _uiState.value = TaskUiState.Loading

        viewModelScope.launch {
            try {
                val tasks = repository.getTasks(token, groupId, limit, offset)
                _uiState.value = TaskUiState.Success(tasks)
            } catch (e: HttpException) {
                _uiState.value = TaskUiState.Error("Error: ${e.message()}")
            } catch (e: Exception) {
                _uiState.value = TaskUiState.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }
}
