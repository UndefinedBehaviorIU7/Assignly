package com.example.assignly.presentation.addGroup

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignly.api.NetworkService
import com.example.assignly.api.models.User
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream

class AddGroupViewModel(application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<AddGroupUiState>(AddGroupUiState.Idle())
    val uiState = _uiState.asStateFlow()

    private val sharedPref = getApplication<Application>()
        .getSharedPreferences("auth", Context.MODE_PRIVATE)
    val id = sharedPref.getInt("id", 0)

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
            _uiState.value = AddGroupUiState.Idle(
                allUsers = if (result.code == 0) result.result else emptyList(),
            )
        }
    }

    fun updateUiState(newName: String? = null,
                      newDescription: String? = null,
                      newMenuExtended: Boolean? = null,
                      newImage: Uri? = null,
                      newMembers: MutableList<User>? = null) {

        when (val current = _uiState.value) {
            is AddGroupUiState.Idle -> {
                _uiState.value = current.copy(
                    name = newName ?: current.name,
                    description = newDescription ?: current.description,
                    image = newImage ?: current.image,
                    members = newMembers ?: current.members,
                    menuExpanded = newMenuExtended ?: current.menuExpanded
                )
            }

            is AddGroupUiState.Error -> {
                _uiState.value = AddGroupUiState.Idle (
                    name = current.name,
                    description = current.description,
                    image = current.image,
                    members = current.members,
                    allUsers = current.allUsers,
                    menuExpanded = current.menuExpanded,
                    membersFieldPosition = current.membersFieldPosition
                )
            }

            else -> Unit
        }
    }

    @ExperimentalStdlibApi
    fun addGroup() {
        val current = _uiState.value
        if (current is AddGroupUiState.Idle) {
            if (current.name.isBlank() || current.description.isBlank()) {
                _uiState.value = AddGroupUiState.Error(
                    name = current.name,
                    description = current.description,
                    image = current.image,
                    members = current.members,
                    allUsers = current.allUsers,
                    menuExpanded = current.menuExpanded,
                    membersFieldPosition = current.membersFieldPosition,
                    errorMessage = "Fields should not be blank"
                )

                return
            }

            _uiState.value = AddGroupUiState.Loading(
                name = current.name,
                description = current.description,
                image = current.image,
                members = current.members,
                allUsers = current.allUsers,
                menuExpanded = current.menuExpanded,
                membersFieldPosition = current.membersFieldPosition
            )

            viewModelScope.launch {
                try {
                    val imageUri = current.image
                    val imagePart = imageUri?.let { createMultipartBodyPart(it) }
                    val sharedPref = getApplication<Application>()
                        .getSharedPreferences("auth", Context.MODE_PRIVATE)
                    val token = sharedPref.getString("token", "")

                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val jsonAdapter: JsonAdapter<List<User>> = moshi.adapter<List<User>>()

                    val json = jsonAdapter.toJson(current.members)

                    val request = NetworkService.api.addGroup(
                        token = token!!.toRequestBody(),
                        name = current.name.toRequestBody(),
                        description = current.description.toRequestBody(),
                        image = imagePart,
                        members = json.toRequestBody()
                    )

                    _uiState.value = AddGroupUiState.Success(
                        successMessage = "Group added successfully"
                    )

                } catch (e: Exception) {
                    var errorMessage = ""
                    when (e) {
                        is HttpException -> {
                            if (e.code() == 400) {
                                errorMessage = "group already exist"
                            } else if (e.code() == 401) {
                                errorMessage = "user is unauthorized"
                            }
                        }

                        else -> {
                            errorMessage = "no internet connection"
                            Log.d("ERROR", e.toString())
                        }
                    }

                    _uiState.value = AddGroupUiState.Error(
                        name = current.name,
                        description = current.description,
                        image = current.image,
                        members = current.members,
                        allUsers = current.allUsers,
                        menuExpanded = current.menuExpanded,
                        membersFieldPosition = current.membersFieldPosition,
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }

    private fun createMultipartBodyPart(uri: Uri): MultipartBody.Part? {
        val file = uriToFile(uri) ?: return null
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

    private fun uriToFile(uri: Uri): File? {
        val context = getApplication<Application>().applicationContext
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        inputStream.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    fun membersToString(members: List<User>): String {
        return members.joinToString(" ") { it.tag }
    }
}