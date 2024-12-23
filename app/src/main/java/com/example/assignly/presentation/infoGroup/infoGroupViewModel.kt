package com.example.assignly.presentation.infoGroup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignly.api.NetworkService
import com.example.assignly.api.models.Group
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class RequestResult(
    val code: Int,
    val result: Group?
)

class infoGroupViewModel(application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<infoGroupUIState>(infoGroupUIState.Idle())
    val uiState = _uiState.asStateFlow()
    private suspend fun loadInfoGroup(): RequestResult {
        try {
            val request = NetworkService.api.groupById(token = "1", groupId = 22)
            return RequestResult(
                code = 0,
                result = request
            )
        } catch (e: Exception) {
            return RequestResult (
                code = 1,
                result = null
            )
        }
    }

    init {
        viewModelScope.launch {
            val result = loadInfoGroup()
            _uiState.value = infoGroupUIState.Idle(
                groupId = 21,
                name = result.result!!.name,
                description = result.result.description,
//                image = result.result.image,
                members = result.result.members
            )
        }
    }


}