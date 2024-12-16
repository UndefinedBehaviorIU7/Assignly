package com.example.assignly.presentation.addGroup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddGroupViewModel(application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(AddGroupUiState.Idle())
    val uiState = _uiState.asStateFlow()
}