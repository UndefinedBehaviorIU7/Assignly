package com.example.assignly.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
КАК ОБРАТИТЬСЯ К API

Внутри ViewModel (на примере аутентификации):

val request = NetworkService.auth.authenticate(
    // Эти параметры из authenticate в интерфейсе AuthAPI
    login,
    password
)

Формат ответа либо в доке к апи, либо в models
 */

object NetworkService {
    // TODO: не забыть поменять на хосте
    private const val BASE_URL = "http://127.0.0.1:8000"

    val auth: AuthAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthAPI::class.java)
    }

    val groupsList: GroupsAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GroupsAPI::class.java)
    }

    val tasksList: TasksAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TasksAPI::class.java)
    }

    val signup: SignupAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SignupAPI::class.java)
    }

    val addTask: AddTaskAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AddTaskAPI::class.java)
    }

    val userById: UserByIdAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserByIdAPI::class.java)
    }

    val allUsers: AllUsersAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AllUsersAPI::class.java)
    }

    val logout: LogoutAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LogoutAPI::class.java)
    }
}
