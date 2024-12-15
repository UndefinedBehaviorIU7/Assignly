package com.example.assignly.presentation.taskList

import com.example.assignly.api.AssignlyAPI
import com.example.assignly.api.models.Task

class TaskRepository(private val api: AssignlyAPI) {
    suspend fun getTasks(token: String, groupId: Int, limit: Int, offset: Int): List<Task> {
        return api.getTasks(token, groupId, limit, offset)
    }
}