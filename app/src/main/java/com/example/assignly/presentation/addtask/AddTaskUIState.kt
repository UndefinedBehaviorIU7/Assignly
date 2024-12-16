package com.example.assignly.presentation.addtask

import android.net.Uri

sealed class AddTaskUIState {
    data class AddTask (
        val title: String = "",
        val assign: String = "", // возможно поменять (если будет класс юзеров)
        val summary: String = "",
        val description: String = "",
        var deadline: String = ""
    ): AddTaskUIState()

    data class Error (
        val title: String,
        val assign: String, // возможно поменять (если будет класс юзеров)
        val summary: String,
        val description: String,
        val deadline: String,
        val errorMessage: String
    ): AddTaskUIState()
}
