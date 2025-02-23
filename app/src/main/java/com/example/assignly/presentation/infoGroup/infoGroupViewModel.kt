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

class infoGroupViewModel(application: Application, groupId: Int, token: String): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<infoGroupUIState>(infoGroupUIState.Idle())
    val uiState = _uiState.asStateFlow()
    private val privateGroupId = groupId
    private val privatetoken = token
    private suspend fun loadInfoGroup(): RequestResult {
        try {
            val request = NetworkService.api.groupById(token = privatetoken, groupId = privateGroupId)
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
                groupId = groupId,
                name = result.result!!.name,
                description = result.result.description,
//                image = result.result.image,
                members = result.result.members
            )
        }
    }


}