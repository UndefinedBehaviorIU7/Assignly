package com.example.assignly.presentation.taskList

import com.example.assignly.api.AssignlyAPI
import com.example.assignly.api.models.TasksList

class TaskRepository(private val api: AssignlyAPI) {
    suspend fun getTasks(token: String, groupId: Int, limit: Int, offset: Int): List<TasksList> {
        return api.getTasks(token, groupId, limit, offset)
    }
}