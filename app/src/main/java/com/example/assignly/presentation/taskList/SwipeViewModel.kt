package com.example.assignly.presentation.taskList

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class SwipeState{
    Default, Left, Right
}

class SwipeViewModel : ViewModel() {
    private val _swipeState = MutableStateFlow(SwipeState.Default)
    val swipeState: StateFlow<SwipeState> = _swipeState

    fun updateSwipeState(newState: SwipeState) {
        _swipeState.value = newState
    }

}