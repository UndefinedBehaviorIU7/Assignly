package com.example.assignly.presentation.addGroup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.assignly.api.NetworkService
import com.example.assignly.api.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddGroupViewModel(application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(AddGroupUiState.Idle())
    val uiState = _uiState.asStateFlow()

    suspend fun loadUsers(): RequestResult {
        try {
            val request = NetworkService.api.allUsers()
            return RequestResult(
                code = 0,
                result = request
            )
        } catch (e: Exception) {
            return RequestResult (
                code = 1,
                result = emptyList()
            )
        }
    }

    init {
        viewModelScope.launch {
            val current = _uiState.value
            val result = loadUsers()
            _uiState.value = AddGroupUiState.Idle(
                name = current.name,
                description = current.description,
                members = current.members,
                allUsers = if (result.code == 0) result.result else emptyList(),
                image = current.image,
                menuExpanded = current.menuExpanded
            )
        }
    }

    fun nameChange(newName: String) {
        when (val current = _uiState.value) {
            is AddGroupUiState.Idle -> {
                _uiState.value = current.copy(name = newName)
            }

            is AddGroupUiState.Error -> {
                _uiState.value = AddGroupUiState.Idle (
                    name = current.name,
                    description = current.description,
                    members = current.members,
                    allUsers = current.allUsers,
                    image = current.image,
                    menuExpanded = current.menuExpanded
                )
            }

            else -> Unit
        }
    }

    fun descriptionChange(newDescription: String) {
        when (val current = _uiState.value) {
            is AddGroupUiState.Idle -> {
                _uiState.value = current.copy(description = newDescription)
            }

            is AddGroupUiState.Error -> {
                _uiState.value = AddGroupUiState.Idle (
                    name = current.name,
                    description = current.description,
                    members = current.members,
                    allUsers = current.allUsers,
                    image = current.image,
                    menuExpanded = current.menuExpanded
                )
            }

            else -> Unit
        }
    }

    fun membersChange() {

    }

    fun membersToString(members: List<User>): String {
        var outString: String = ""
        for (item in members) {
            outString += item.tag
            outString += " "
        }
        return outString
    }
}