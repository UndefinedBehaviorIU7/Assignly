package com.example.assignly.presentation.groupList

import android.app.Application
import android.content.Context
import android.health.connect.datatypes.HeartRateRecord
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignly.api.NetworkService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class GroupViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<GroupUiState>(GroupUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val repository = GroupRepository(NetworkService.api)

    private val sharedPref = getApplication<Application>()
        .getSharedPreferences("auth", Context.MODE_PRIVATE)
    val token = sharedPref.getString("token", "")

    fun fetchGroups(token: String) {
        _uiState.value = GroupUiState.Loading
        viewModelScope.launch {
            try {
                val groups = repository.getGroups(token)
                _uiState.value = GroupUiState.All(groups)
            } catch (e: HttpException) {
                _uiState.value = GroupUiState.Error(emptyList(), "Error: ${e.message()}")
            } catch (e: Exception) {
                _uiState.value = GroupUiState.Error(emptyList(),"Unexpected error: ${e.localizedMessage}")
            }
        }
    }

    fun searchGroups(query: String) {
        val currentState = _uiState.value

        if (query.isEmpty()) {
            if (currentState is GroupUiState.Search) {
                _uiState.value = GroupUiState.All(currentState.groups)
            }
        } else {
            val groups = when (currentState) {
                is GroupUiState.All -> {
                    currentState.groups
                }

                is GroupUiState.Search -> {
                    currentState.groups
                }

                else -> {
                    emptyList()
                }
            }

            val filteredGroups = groups.filter {
                it.name.contains(query, ignoreCase = true)
            }

            _uiState.value = GroupUiState.Search(groups, filteredGroups)
        }
    }

    fun getImage(path: String) {
        viewModelScope.launch {
            try {
                val request = NetworkService.api.getImage(path)
            } catch (e: HttpException) {
                // TODO: возврат ошибки через стейты
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            try {
                val request = NetworkService.api.logout(token.toString())
            } catch (e: HttpException) {
                // TODO: возврат ошибки через стейты
            }
        }
    }
}
