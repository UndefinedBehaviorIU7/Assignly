package com.example.assignly.presentation.addGroup

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignly.api.NetworkService
import com.example.assignly.api.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AddGroupViewModel(application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<AddGroupUiState>(AddGroupUiState.Idle())
    val uiState = _uiState.asStateFlow()

    private suspend fun loadUsers(): RequestResult {
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
            val result = loadUsers()
            _uiState.value = AddGroupUiState.Idle(
                allUsers = if (result.code == 0) result.result else emptyList(),
            )
        }
    }

    fun updateUiState(newName: String? = null,
                      newDescription: String? = null,
                      newMenuExtended: Boolean? = null,
                      newImage: Uri? = null,
                      newMembers: MutableList<User>? = null) {

        when (val current = _uiState.value) {
            is AddGroupUiState.Idle -> {
                _uiState.value = current.copy(
                    name = newName ?: current.name,
                    description = newDescription ?: current.description,
                    image = newImage ?: current.image,
                    members = newMembers ?: current.members,
                    menuExpanded = newMenuExtended ?: current.menuExpanded
                )
            }

            is AddGroupUiState.Error -> {
                _uiState.value = AddGroupUiState.Idle (
                    name = current.name,
                    description = current.description,
                    image = current.image,
                    members = current.members,
                    allUsers = current.allUsers,
                    menuExpanded = current.menuExpanded,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }

    fun addGroup() {
        val current = _uiState.value
        if (current is AddGroupUiState.Idle) {
            if (current.name.isBlank() || current.description.isBlank()) {
                _uiState.value = AddGroupUiState.Error(
                    name = current.name,
                    description = current.description,
                    image = current.image,
                    members = current.members,
                    allUsers = current.allUsers,
                    menuExpanded = current.menuExpanded,
                    membersFieldPosition = current.membersFieldPosition,
                    errorMessage = "Fields shoud not be blank"
                )

                return
            }

            _uiState.value = AddGroupUiState.Loading(
                name = current.name,
                description = current.description,
                image = current.image,
                members = current.members,
                allUsers = current.allUsers,
                menuExpanded = current.menuExpanded,
                membersFieldPosition = current.membersFieldPosition
            )

            viewModelScope.launch {
                try {

                } catch (e: Exception) {
                    var errorMessage: String = ""
                    when (e) {
                        is HttpException -> {
                            if (e.code() == 400) {
                                errorMessage = "group already exist"
                            } else if (e.code() == 401) {
                                errorMessage = "user is unauthorized"
                            }
                        }

                        else -> errorMessage = "no internet connection"
                    }

                    _uiState.value = AddGroupUiState.Error(
                        name = current.name,
                        description = current.description,
                        image = current.image,
                        members = current.members,
                        allUsers = current.allUsers,
                        menuExpanded = current.menuExpanded,
                        membersFieldPosition = current.membersFieldPosition,
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }

    fun membersToString(members: List<User>): String {
        return members.joinToString(" ") { it.tag }
    }
}