package com.example.assignly.presentation.addtask

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun AddTask(navController: NavController, vm: AddTaskViewModel = viewModel()) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.weight(0.7f))


            when (val uiState = vm.uiState.collectAsState().value) {
                is AddTaskUIState.Idle -> {
                    Column(
                        modifier = Modifier.weight(1.2f)
                    ) {

                        Form (value = uiState.name, label = stringResource(R.string.login),
                            isError = false, lambda = { vm.nameChange(it) })

                        Form (value = uiState.description, label = stringResource(R.string.password),
                            isError = false, lambda = { vm.descriptionChange(it) })

                        Form (value = uiState.deadline, label = stringResource(R.string.password),
                            isError = false, lambda = { vm.deadlineChange(it) })

//                        Form (value = uiState.members, label = stringResource(R.string.password),
//                            isError = false, lambda = { vm.membersChange(it) })

                        Form (value = uiState.summary, label = stringResource(R.string.password),
                            isError = false, lambda = { vm.summaryChange(it) })

                        Spacer(modifier = Modifier.weight(0.5f))
                    }
                }

//                is AddTaskUIState.Error -> {
//                    Column(
//                        modifier = Modifier.weight(1.3f)
//                    ) {
//
//                        Form (value = uiState.login, label = stringResource(R.string.login),
//                            isError = true, lambda = { vm.loginChange(it) })
//
//                        Form (value = uiState.password, label = stringResource(R.string.password),
//                            isError = true, lambda = { vm.passwordChange(it) })
//
//                        Spacer(modifier = Modifier.weight(0.5f))
//                        Text(
//                            text = "Error: ${uiState.errorMessage}",
//                            color = Color.Red,
//                            modifier = Modifier.align(Alignment.CenterHorizontally)
//                                .weight(0.5f),
//                        )
//                        Spacer(modifier = Modifier.weight(0.1f))
//                    }
//                }

                is AddTaskUIState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                else -> Unit
            }

            Column (horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(0.5f)) {
                Button(
                    onClick = { vm.addtask()
                        navController.navigate("task_list")},
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
                        text = stringResource(R.string.signup),
                        fontSize = 25.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.6f))
        }
    }
}

