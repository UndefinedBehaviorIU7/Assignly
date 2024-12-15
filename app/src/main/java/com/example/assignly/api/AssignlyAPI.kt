package com.example.assignly.api

import com.example.assignly.api.models.Auth
import com.example.assignly.api.models.GroupsList
import com.example.assignly.api.models.Response
import com.example.assignly.api.models.TasksList
import com.example.assignly.api.models.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AssignlyAPI {
    @GET("/login/")
    suspend fun authenticate (
        @Query("login") login: String,
        @Query("password") password: String
    ): Auth

    @GET("/groups/")
    suspend fun getGroups (
        @Query("token") token: String
    ): GroupsList

    @GET("/tasks")
    suspend fun getTasks (
        @Query("token") token: String,
        @Query("group_id") groupId: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): TasksList

    @POST("/signup")
    suspend fun signup (
        @Query("login") login: String,
        @Query("tag") tag: String,
        @Query("password") password: String,
        @Query("img") image: String
    ): Response

    @POST("/add_task")
    suspend fun addTask (
        @Query("group_id") groupId: Int,
        @Query("owner_id") ownerId: Int,
        @Query("name") name: String,
        @Query("description") description: String,
        @Query("summary") summary: String,
        @Query("deadline") deadline: String,
        @Query("status") status: Int,
        @Query("members") members: List<Int>
    )

    @GET("user_by_id")
    suspend fun userById (
        @Query("id") id: Int
    ): User

    @GET("/users")
    suspend fun allUsers (
    ): List<User>

    @POST("/logout")
    suspend fun logout (
        @Query("token") token: String
    ): Response

    @POST("/add_group")
    suspend fun addGroup (
        @Query("token") token: String,
        @Query("name") name: String,
        @Query("description") description: String,
        @Query("image") image: String,
        @Query("members") members: List<Int>
    )

    @GET("/group_by_id")
    suspend fun groupById (
        @Query("token") token: String,
        @Query("group_id") groupId: Int
    )

    @GET("/task_by_id")
    suspend fun taskById (
        @Query("token") token: String,
        @Query("task_id") taskId: Int
    )
}
