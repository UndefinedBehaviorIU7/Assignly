package com.example.assignly.api

import com.example.assignly.api.models.Auth
import com.example.assignly.api.models.GroupsList
import com.example.assignly.api.models.Response
import com.example.assignly.api.models.TasksList
import com.example.assignly.api.models.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// Аутентификация
interface AuthAPI {
    @GET("/login/")

    suspend fun authenticate (
        @Query("login") login: String,
        @Query("password") password: String
    ): Auth
}

// Список групп
interface GroupsAPI {
    @GET("/groups/")

    suspend fun getGroups (
        @Query("token") token: String
    ): GroupsList
}

// Список задач в группе
interface TasksAPI {
    @GET("/tasks")

    suspend fun getTasks (
        @Query("token") token: String,
        @Query("group_id") groupId: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): TasksList
}

// Регистрация
interface SignupAPI {
    @POST("/signup")

    suspend fun signup (
        @Query("login") login: String,
        @Query("tag") tag: String,
        @Query("password") password: String,
        @Query("img") image: String
    ): Response
}

// Добавление задачи
interface AddTaskAPI {
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
}

// Получение пользователя по id
interface UserByIdAPI {
    @GET("user_by_id")

    suspend fun userById (
        @Query("id") id: Int
    ): User
}

// Получение всех пользователей
interface AllUsersAPI {
    @GET("/users")

    suspend fun allUsers (
    ): List<User>
}

// Выход
interface LogoutAPI {
    @POST("/logout")

    suspend fun logout (
        @Query("token") token: String
    ): Response
}
