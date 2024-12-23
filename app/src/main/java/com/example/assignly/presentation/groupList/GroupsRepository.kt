package com.example.assignly.presentation.groupList

import android.net.http.HttpResponseCache.install
import com.example.assignly.api.AssignlyAPI
import com.example.assignly.api.models.Group
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient

class GroupRepository(private val api: AssignlyAPI) {
    suspend fun getGroups(token: String): List<Group> {
        return api.getGroups(token)
    }
}
