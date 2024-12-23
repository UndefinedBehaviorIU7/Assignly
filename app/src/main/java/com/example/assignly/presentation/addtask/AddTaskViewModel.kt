package com.example.assignly.presentation.addtask

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignly.R
import com.example.assignly.api.NetworkService
import com.example.assignly.api.models.User
import com.example.assignly.presentation.addtask.AddTaskUIState
import com.example.assignly.presentation.signup.SignupUiState
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import com.squareup.moshi.adapter


data class RequestResult (
    val code: Int,
    val result: List<User>
)

class AddTaskViewModel(application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<AddTaskUIState>(AddTaskUIState.Idle())
    val uiState = _uiState.asStateFlow()
    private suspend fun loadUsers(): RequestResult {
        try {
            val request = NetworkService.api.allUsers()
            return RequestResult(
                code = 0,
                result = request
            )
        } catch (e: Exception) {
            return RequestResult (
                code = 1,
                result = emptyList()
            )
        }
    }

    init {
        viewModelScope.launch {
            val result = loadUsers()
            _uiState.value = AddTaskUIState.Idle(
                allUsers = if (result.code == 0) result.result else emptyList(),
                ownerId = 1,
                groupId = 21,
                token = "1",
                status = 0
            )
        }
    }

    fun updateUIState(
        newName: String? = null,
        newSummary: String? = null,
        newDescription: String? = null,
        newDeadlineData: String? = null,
        newDeadlineTime: String? = null,
        newMembers: MutableList<User>? = null,
        newStatus: Int? = null,
        newMenuExtended: Boolean? = null,
    ) {
        when (val current = _uiState.value) {
            is AddTaskUIState.Idle -> {
                _uiState.value = current.copy(
                    token = current.token, // мб исправить
                    name = newName ?: current.name,
                    summary = newSummary ?: current.summary,
                    description = newDescription ?: current.description,
                    deadlinedata = newDeadlineData ?: current.deadlinedata,
                    deadlinetime = newDeadlineTime ?: current.deadlinetime,
                    members = newMembers ?: current.members,
                    status = newStatus ?: current.status,
                    menuExpanded = newMenuExtended ?: current.menuExpanded
                )
            }

            is AddTaskUIState.Error -> {
                _uiState.value = AddTaskUIState.Idle(
                    token = current.token,
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }

    fun nameChange(newName: String) {
        when (val current = _uiState.value) {
            is AddTaskUIState.Idle -> {
                _uiState.value = current.copy(name = newName)
            }

            is AddTaskUIState.Error -> {
                _uiState.value = AddTaskUIState.Idle(
                    token = current.token,
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }


    fun membersChange(newMembers: MutableList<User>) {
        when (val current = _uiState.value) {
            is AddTaskUIState.Idle -> {
                _uiState.value = current.copy(members = newMembers)
            }

            is AddTaskUIState.Error -> {
                _uiState.value = AddTaskUIState.Idle(
                    token = current.token,
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }

    fun summaryChange(newSummary: String) {
        when (val current = _uiState.value) {
            is AddTaskUIState.Idle -> {
                _uiState.value = current.copy(summary = newSummary)
            }

            is AddTaskUIState.Error -> {
                _uiState.value = AddTaskUIState.Idle(
                    token = current.token,
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }

    fun descriptionChange(newDescription: String) {
        when (val current = _uiState.value) {
            is AddTaskUIState.Idle -> {
                _uiState.value = current.copy(description = newDescription)
            }

            is AddTaskUIState.Error -> {
                _uiState.value = AddTaskUIState.Idle(
                    token = current.token,
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }

    fun deadlinedataChange(newDeadlineData: String) {
        when (val current = _uiState.value) {
            is AddTaskUIState.Idle -> {
                _uiState.value = current.copy(deadlinedata = newDeadlineData)
            }

            is AddTaskUIState.Error -> {
                _uiState.value = AddTaskUIState.Idle(
                    token = current.token,
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }

    fun deadlinetimeChange(newDeadlineTime: String) {
        when (val current = _uiState.value) {
            is AddTaskUIState.Idle -> {
                _uiState.value = current.copy(deadlinetime = newDeadlineTime)
            }

            is AddTaskUIState.Error -> {
                _uiState.value = AddTaskUIState.Idle(
                    token = current.token,
                    groupId = current.groupId,
                    ownerId = current.ownerId,
                    name = current.name,
                    description = current.description,
                    summary = current.summary,
                    deadlinedata = current.deadlinedata,
                    deadlinetime = current.deadlinetime,
                    status = current.status,
                    members = current.members,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }

    fun createErrorState(
        current: AddTaskUIState.Idle,
    ): AddTaskUIState.Error {
        return AddTaskUIState.Error(
            token = current.token,
            groupId = current.groupId,
            ownerId = current.ownerId,
            name = current.name,
            description = current.description,
            summary = current.summary,
            deadlinedata = current.deadlinedata,
            deadlinetime = current.deadlinetime,
            status = current.status,
            members = current.members,
            membersFieldPosition = current.membersFieldPosition,
            menuExpanded = current.menuExpanded,
            allUsers = current.allUsers,
            errorMessage = TODO(),
            errorField = TODO(),
        )
    }
    
    @OptIn(ExperimentalStdlibApi::class)
    fun addtask() {
        val current = _uiState.value

        if (current is AddTaskUIState.Idle) {

            if (current.name.isBlank())
            {
                _uiState.value = createErrorState(current).copy(
                    errorMessage = "Name cannot be blank",
                    errorField = "name"
                )

                return
            }

            if (current.description.isBlank())
            {
                _uiState.value = createErrorState(current).copy(
                    errorMessage = "Description cannot be blank",
                    errorField = "description"
                )
                return
            }

            if (current.summary.isBlank())
            {
                _uiState.value = createErrorState(current).copy(
                    errorMessage = "Summary cannot be blank",
                    errorField = "summary"
                )
                return
            }


            _uiState.value = AddTaskUIState.Loading(
                token = current.token,
                groupId = current.groupId,
                ownerId = current.ownerId,
                name = current.name,
                description = current.description,
                summary = current.summary,
                deadlinedata = current.deadlinedata,
                deadlinetime = current.deadlinetime,
                status = current.status,
                members = current.members,
                membersFieldPosition = current.membersFieldPosition,
                menuExpanded = current.menuExpanded,
                allUsers = current.allUsers
            )

            viewModelScope.launch {
                try {
                    Log.d("qqqqq", current.name +
                    " " + current.description +
                    " " + current.summary +
                    " " + dedlineGen(current.deadlinedata, current.deadlinetime) +
                    " " + current.members)

                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val jsonAdapter: JsonAdapter<List<User>> = moshi.adapter<List<User>>()

                    val json = jsonAdapter.toJson(current.members)
                    val request = NetworkService.api.addTask(
                        token = current.token,
                        groupId = current.groupId,
                        name = current.name,
                        description = current.description,
                        summary = current.summary,
                        deadline = dedlineGen(current.deadlinedata, current.deadlinetime),
                        status = current.status,
                        members = json
                    )
                    _uiState.value = AddTaskUIState.Success (
                        text = "ok"
                    )
                } catch (e: HttpException) {
                    Log.d("codeError", e.code().toString())
                    _uiState.value = AddTaskUIState.Error(
                        token = current.token,
                        groupId = current.groupId,
                        ownerId = current.ownerId,
                        name = current.name,
                        description = current.description,
                        summary = current.summary,
                        deadlinedata = current.deadlinedata,
                        deadlinetime = current.deadlinetime,
                        status = current.status,
                        members = current.members,
                        membersFieldPosition = current.membersFieldPosition,
                        menuExpanded = current.menuExpanded,
                        allUsers = current.allUsers,
                        errorMessage = "q", // TODO: исправить
                        errorField = "q", // TODO: исправить
                    )
                }
            }
        }
    }


    fun membersToString(members: List<User>): String {
        return members.joinToString(" ") { it.tag }
    }

    fun dedlineGen(data: String, time: String): String{
        return  data + " " + time
    }

//    fun isValidFutureDate(date: String, time: String): Boolean {
//        // Проверяем, что строки не пустые
//        if (date.isBlank() || time.isBlank()) return false
//
//        // Разбиваем дату и время на компоненты
//        val dateParts = date.split(".")
//        val timeParts = time.split(":")
//
//        // Проверяем, что формат даты и времени корректен
//        if (dateParts.size != 3 || timeParts.size != 2) return false
//
//        return try {
//            // Извлекаем значения из строк
//            val day = dateParts[0].toInt()
//            val month = dateParts[1].toInt()
//            val year = dateParts[2].toInt()
//
//            val hour = timeParts[0].toInt()
//            val minute = timeParts[1].toInt()
//
//            // Создаём объект LocalDateTime из введённых данных
//            val inputDateTime = LocalDateTime.of(year, month, day, hour, minute)
//
//            // Сравниваем с текущим временем
//            inputDateTime.isAfter(LocalDateTime.now())
//        } catch (e: Exception) {
//            false // Возвращаем false, если возникла ошибка парсинга
//        }
//    }


}