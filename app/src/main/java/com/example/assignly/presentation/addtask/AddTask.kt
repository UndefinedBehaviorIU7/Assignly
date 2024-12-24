package com.example.assignly.presentation.addtask

import android.app.Application
import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.assignly.R
import com.example.assignly.presentation.Navigation
import com.example.assignly.presentation.forms.ButtonForm
import com.example.assignly.presentation.forms.Form
import com.example.assignly.presentation.addtask.AddTaskViewModel
import com.example.assignly.presentation.addtask.AddTaskUIState
import com.example.assignly.presentation.forms.FormAddData
import com.example.assignly.presentation.forms.FormAddTime
import com.example.assignly.presentation.signup.SignupUiState
import java.util.Calendar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.material3.Text as Text

@Composable
fun MemberField(uiState: AddTaskUIState, vm: AddTaskViewModel) {
    if (uiState is AddTaskUIState.Idle) {
        Box(modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth()) {
            OutlinedTextField(
                value = vm.membersToString(uiState.members),
                onValueChange = { vm.membersChange(newMembers = uiState.members) },
                label = { Text(text = "members") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.onSurface
                ),
                trailingIcon = {
                    Icon(
                        imageVector = if (uiState.menuExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = "arrow",
                        modifier = Modifier.clickable { vm.updateUIState(newMenuExtended = !uiState.menuExpanded) }
                    )
                },
                readOnly = true,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        uiState.membersFieldPosition = coordinates.size.toSize()
                    }
            )

            DropdownMenu(
                expanded = uiState.menuExpanded,
                onDismissRequest = { vm.updateUIState(newMenuExtended = false) },
                modifier = Modifier
                    .width(with(LocalDensity.current) { uiState.membersFieldPosition.width.toDp() })
                    .height(300.dp)
            ) {
                uiState.allUsers.forEach { user ->
                    DropdownMenuItem(
                        text = { Text(text = user.tag) },
                        onClick = {
                            if (user !in uiState.members) {
                                uiState.members.add(user)
                            } else {
                                uiState.members.remove(user)
                            }
                            vm.updateUIState(newMenuExtended = false)
                        }
                    )
                }
            }
        }
    }

    if (uiState is AddTaskUIState.Error) {
        Box(modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth()) {
            OutlinedTextField(
                value = vm.membersToString(uiState.members),
                onValueChange = { vm.membersChange(newMembers = uiState.members) },
                label = { Text(text = "members") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.error,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.onSurface
                ),
                trailingIcon = {
                    Icon(
                        imageVector = if (uiState.menuExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = "arrow",
                        modifier = Modifier.clickable { vm.updateUIState(newMenuExtended = !uiState.menuExpanded) }
                    )
                },
                readOnly = true,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        uiState.membersFieldPosition = coordinates.size.toSize()
                    }
            )

            DropdownMenu(
                expanded = uiState.menuExpanded,
                onDismissRequest = { vm.updateUIState(newMenuExtended = false) },
                modifier = Modifier
                    .width(with(LocalDensity.current) { uiState.membersFieldPosition.width.toDp() })
                    .height(300.dp)
            ) {
                uiState.allUsers.forEach { user ->
                    DropdownMenuItem(
                        text = { Text(text = user.tag) },
                        onClick = {
                            if (user !in uiState.members) {
                                uiState.members.add(user)
                            } else {
                                uiState.members.remove(user)
                            }
                            vm.updateUIState(newMenuExtended = false)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddTask(navController: NavController, groupId: Int, token: String)
{
    val context = LocalContext.current

    val vm: AddTaskViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AddTaskViewModel(
                    application = context.applicationContext as Application,
                    groupId = groupId,
                    token = token
                ) as T
            }
        }
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.weight(0.2f))


            when (val uiState = vm.uiState.collectAsState().value) {
                is AddTaskUIState.Idle -> {
                    Column(
                        modifier = Modifier
                            .weight(1.3f)
                            .padding(horizontal = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Form (value = uiState.name, label = stringResource(R.string.title),
                            isError = false, lambda = { vm.updateUIState(newName = it) })

                        Form (value = uiState.description, label = stringResource(R.string.description),
                            isError = false, lambda = { vm.updateUIState(newDescription = it) })

                        Form (value = uiState.summary, label = stringResource(R.string.summary),
                            isError = false, lambda = { vm.updateUIState(newSummary = it) })

                        FormAddData(value = uiState.deadlinedata, label = stringResource(R.string.deadlinedata),
                            isError = false, lambda = { vm.updateUIState(newDeadlineData = it) })

                        FormAddTime(value = uiState.deadlinetime, label = stringResource(R.string.deadlinetime),
                            isError = false, lambda = { vm.updateUIState(newDeadlineTime = it) })

                        Box(modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth()) {
                            OutlinedTextField(
                                value = vm.membersToString(uiState.members),
                                onValueChange = { vm.membersChange(newMembers = uiState.members) },
                                label = { Text(text = "members") },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.background,
                                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                    cursorColor = MaterialTheme.colorScheme.onSurface
                                ),
                                trailingIcon = {
                                    Icon(
                                        imageVector = if (uiState.menuExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                        contentDescription = "arrow",
                                        modifier = Modifier.clickable { vm.updateUIState(newMenuExtended = !uiState.menuExpanded) }
                                    )
                                },
                                readOnly = true,
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        uiState.membersFieldPosition = coordinates.size.toSize()
                                    }
                            )

                            DropdownMenu(
                                expanded = uiState.menuExpanded,
                                onDismissRequest = { vm.updateUIState(newMenuExtended = false) },
                                modifier = Modifier
                                    .width(with(LocalDensity.current) { uiState.membersFieldPosition.width.toDp() })
                                    .height(300.dp)
                            ) {
                                uiState.allUsers.forEach { user ->
                                    DropdownMenuItem(
                                        text = { Text(text = user.tag) },
                                        onClick = {
                                            if (user !in uiState.members) {
                                                uiState.members.add(user)
                                            } else {
                                                uiState.members.remove(user)
                                            }
                                            vm.updateUIState(newMenuExtended = false)
                                        }
                                    )
                                }
                            }
                        }

//                        Spacer(modifier = Modifier.weight(0.6f))


                    }
                }

                is AddTaskUIState.Error -> {
                    Column(
                        modifier = Modifier
                            .weight(1.3f)
                            .padding(horizontal = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Form (value = uiState.name, label = stringResource(R.string.title),
                            isError = if (uiState.errorField == "name") true else false, lambda = { vm.nameChange(it) })

                        Form (value = uiState.description, label = stringResource(R.string.description),
                            isError = if (uiState.errorField == "description") true else false, lambda = { vm.descriptionChange(it) })

                        Form (value = uiState.summary, label = stringResource(R.string.summary),
                            isError = if (uiState.errorField == "summary") true else false, lambda = { vm.summaryChange(it) })

                        FormAddData(value = uiState.deadlinedata, label = stringResource(R.string.deadlinedata),
                            isError = if (uiState.errorField == "data") true else false, lambda = { vm.deadlinedataChange(it) })

                        FormAddTime(value = uiState.deadlinetime, label = stringResource(R.string.deadlinetime),
                            isError = if (uiState.errorField == "time") true else false, lambda = { vm.deadlinetimeChange(it) })

                        Box(modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth()) {
                            OutlinedTextField(
                                value = vm.membersToString(uiState.members),
                                onValueChange = { vm.membersChange(newMembers = uiState.members) },
                                label = { Text(text = "members") },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                    unfocusedBorderColor = if (uiState.errorField == "members") MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.background,
                                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                    cursorColor = MaterialTheme.colorScheme.onSurface
                                ),
                                trailingIcon = {
                                    Icon(
                                        imageVector = if (uiState.menuExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                        contentDescription = "arrow",
                                        modifier = Modifier.clickable { vm.updateUIState(newMenuExtended = !uiState.menuExpanded) }
                                    )
                                },
                                readOnly = true,
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        uiState.membersFieldPosition = coordinates.size.toSize()
                                    }
                            )

                            DropdownMenu(
                                expanded = uiState.menuExpanded,
                                onDismissRequest = { vm.updateUIState(newMenuExtended = false) },
                                modifier = Modifier
                                    .width(with(LocalDensity.current) { uiState.membersFieldPosition.width.toDp() })
                                    .height(300.dp)
                            ) {
                                uiState.allUsers.forEach { user ->
                                    DropdownMenuItem(
                                        text = { Text(text = user.tag) },
                                        onClick = {
                                            if (user !in uiState.members) {
                                                uiState.members.add(user)
                                            } else {
                                                uiState.members.remove(user)
                                            }
                                            vm.updateUIState(newMenuExtended = false)
                                        }
                                    )
                                }
                            }
                        }

                        Text(
                            text = "Error: ${uiState.errorMessage}",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .weight(0.5f),
                        )

//                        Spacer(modifier = Modifier.weight(0.6f))
                    }
                }

                is AddTaskUIState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is AddTaskUIState.Success -> {
                    Log.d("ok1234","ok")
                    navController.navigate("${Navigation.TASK_LIST}/${groupId}")
                    Text(uiState.text)
                }

                else -> Unit
            }

            Column ( horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(0.5f).fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(0.5f))
                Button(
                    onClick = { vm.addtask() },
                    contentPadding = PaddingValues(
                        top = 10.dp,
                        bottom = 10.dp,
                        start = 20.dp,
                        end = 20.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.active)
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text (
                        text = stringResource(R.string.submit),
                        fontSize = 25.sp
                    )
                }
                Spacer(modifier = Modifier.weight(0.2f))
            }
        }
    }
}
