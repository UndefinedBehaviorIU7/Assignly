package com.example.assignly.presentation.taskList

import android.net.http.HttpResponseCache.install
import com.example.assignly.api.AssignlyAPI
import com.example.assignly.api.models.Group
import com.example.assignly.api.models.Task
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient

class TaskRepository(private val api: AssignlyAPI) {
    suspend fun getTasks(token: String, groupId: Int, limit: Int, offset: Int): List<Task> {
        return api.getTasks(token, groupId, limit, offset)
    }
    suspend fun getGroup(token: String, groupId: Int): Group {
        return api.groupById(token, groupId)
    }

}