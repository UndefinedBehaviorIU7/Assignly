package com.example.assignly.presentation.groupList

import com.example.assignly.api.models.Group

sealed class GroupUiState {
    data object Idle : GroupUiState()
    data object Loading : GroupUiState()

    data class All(val groups: List<Group>) : GroupUiState()

    data class Error(val message: String) : GroupUiState()
}
