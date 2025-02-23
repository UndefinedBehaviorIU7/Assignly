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
    private const val BASE_URL = "http://10.0.2.2:8000"

    val api: AssignlyAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AssignlyAPI::class.java)
    }
}
