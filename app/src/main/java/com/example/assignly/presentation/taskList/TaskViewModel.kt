package com.example.assignly.presentation.taskList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignly.api.NetworkService
import com.example.assignly.api.models.Task
import com.example.assignly.presentation.login.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<TaskUiState>(TaskUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val repository = TaskRepository(NetworkService.api)


    fun fetchTasks(token: String, groupId: Int, limit: Int, offset: Int) {
        _uiState.value = TaskUiState.Loading

        viewModelScope.launch {
            try {
                val tasks = repository.getTasks(token, groupId, limit, offset)
                _uiState.value = TaskUiState.All(tasks)
            } catch (e: HttpException) {
                _uiState.value = TaskUiState.Error("Error: ${e.message()}")
            } catch (e: Exception) {
                _uiState.value = TaskUiState.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }

    fun inProgress(token: String, groupId: Int, limit: Int, offset: Int) {
        when (val current = _uiState.value) {
            is TaskUiState.All -> {
                viewModelScope.launch {
                    _uiState.value = TaskUiState.InProcess(
                        tasks = repository.getTasks(
                            token,
                            groupId,
                            limit,
                            offset
                        ).filter { it.status != 0 })
                }
            }
            is TaskUiState.Done -> {
                viewModelScope.launch {
                    _uiState.value = TaskUiState.InProcess(
                        tasks = repository.getTasks(
                            token,
                            groupId,
                            limit,
                            offset
                        ).filter { it.status != 0 })
                }
            }
            else -> Unit
        }
    }

    fun done(token: String, groupId: Int, limit: Int, offset: Int) {
        when (val current = _uiState.value) {
            is TaskUiState.All -> {
                viewModelScope.launch {
                    _uiState.value = TaskUiState.Done(
                        tasks = repository.getTasks(
                            token,
                            groupId,
                            limit,
                            offset
                        ).filter { it.status == 0 })
                }
            }
            is TaskUiState.InProcess -> {
                viewModelScope.launch {
                    _uiState.value = TaskUiState.Done(
                        tasks = repository.getTasks(
                            token,
                            groupId,
                            limit,
                            offset
                        ).filter { it.status == 0 })
                }
            }
            else -> Unit
        }
    }

}
