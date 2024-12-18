package com.example.assignly.api

import com.example.assignly.api.models.Auth
import com.example.assignly.api.models.Group
import com.example.assignly.api.models.Response
import com.example.assignly.api.models.Task
import com.example.assignly.api.models.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
    ): Group

    @GET("/tasks")
    suspend fun getTasks (
        @Query("token") token: String,
        @Query("group_id") groupId: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<Task>

    @Multipart
    @POST("/signup")
    suspend fun signup (
        @Part("login") login: RequestBody,
        @Part("tag") tag: RequestBody,
        @Part("password") password: RequestBody,
        @Part image: MultipartBody.Part
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

    @Multipart
    @POST("/add_group")
    suspend fun addGroup (
        @Part("token") token: RequestBody,
        @Part("name") name: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("members") members: RequestBody
    ): Response

    @GET("/group_by_id")
    suspend fun groupById (
        @Query("token") token: String,
        @Query("group_id") groupId: Int
    ): Group

    @GET("/task_by_id")
    suspend fun taskById (
        @Query("token") token: String,
        @Query("task_id") taskId: Int
    ): Task

    @POST("/status_change")
    suspend fun statusChange (
        @Query("token") token: String,
        @Query("task_id") taskId: Int,
        @Query("status") status: Int
    ): Response

    @Multipart
    @GET("/get_image")
    suspend fun getImage (
        @Query("path") path: RequestBody
    ): Response
}
