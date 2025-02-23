package com.example.assignly.presentation.taskList

import android.app.Application
import android.content.Context
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

    private val sharedPref = getApplication<Application>()
        .getSharedPreferences("auth", Context.MODE_PRIVATE)
    val token = sharedPref.getString("token", "").toString()

    fun fetchTasks(groupId: Int, limit: Int, offset: Int) {
        _uiState.value = TaskUiState.Loading

        viewModelScope.launch {
            try {
                val tasks = repository.getTasks(token, groupId, limit, offset)
                _uiState.value = TaskUiState.All(tasks, group = repository.getGroup(token, groupId))
            } catch (e: HttpException) {
                _uiState.value = TaskUiState.Error("Error: ${e.message()}")
            } catch (e: Exception) {
                _uiState.value = TaskUiState.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }

    fun inProgress(groupId: Int, limit: Int, offset: Int) {
        when (val current = _uiState.value) {
            is TaskUiState.All -> {
                viewModelScope.launch {
                    _uiState.value = TaskUiState.InProcess(
                        group = repository.getGroup(token, groupId),
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
                        group = repository.getGroup(token, groupId),
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

    fun done(groupId: Int, limit: Int, offset: Int) {
        when (val current = _uiState.value) {
            is TaskUiState.All -> {
                viewModelScope.launch {
                    _uiState.value = TaskUiState.Done(
                        group = repository.getGroup(token, groupId),
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
                        group = repository.getGroup(token, groupId),
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

    fun sendTask(taskId: Int) {
        val sharedPref = getApplication<Application>()
            .getSharedPreferences("auth", Context.MODE_PRIVATE)
        sharedPref.edit()
            .putInt("taskId", taskId)
            .apply()
    }
}
