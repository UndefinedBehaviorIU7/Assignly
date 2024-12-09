package com.example.assignly.presentation.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignly.R

@Composable
fun Signup(vm: SignupViewModel = viewModel()) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                stringResource(R.string.app_name),
                fontSize = 40.sp,
                modifier = Modifier.padding()
            )
            when (val uiState = vm.uiState.collectAsState().value) {
                is SignupUiState.Idle -> {
                    Column(
                        modifier = Modifier.padding(top = 80.dp)
                    ) {
                        OutlinedTextField(
                            value = uiState.login,
                            onValueChange = { vm.loginChange(it) },
                            label = { Text(stringResource(R.string.login)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.active)
                            ),
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        OutlinedTextField(
                            value = uiState.tag,
                            onValueChange = { vm.tagChange(it) },
                            label = { Text(stringResource(R.string.tag)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.active)
                            ),
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        OutlinedTextField(
                            value = uiState.password,
                            onValueChange = { vm.passwordChange(it) },
                            label = { Text(stringResource(R.string.password)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.active)
                            ),
                            modifier = Modifier.padding(bottom = 80.dp)
                        )

                        OutlinedTextField(
                            value = uiState.passwordRepeat,
                            onValueChange = { vm.passwordChange(it) },
                            label = { Text(stringResource(R.string.repeat_password)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.active)
                            ),
                            modifier = Modifier.padding(bottom = 80.dp)
                        )

                        Button(
                            onClick = { vm.signup() },
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
                }

                is SignupUiState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is SignupUiState.Error -> {
                    Column(
                        modifier = Modifier.padding(top = 80.dp)
                    ) {
                        OutlinedTextField(
                            value = uiState.login,
                            onValueChange = { vm.loginChange(it) },
                            label = { Text(stringResource(R.string.login)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.active),
                                unfocusedBorderColor = Color.Red
                            ),
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        OutlinedTextField(
                            value = uiState.tag,
                            onValueChange = { vm.tagChange(it) },
                            label = { Text(stringResource(R.string.tag)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.active),
                                unfocusedBorderColor = Color.Red
                            ),
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        OutlinedTextField(
                            value = uiState.password,
                            onValueChange = { vm.passwordChange(it) },
                            label = { Text(stringResource(R.string.password)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.active),
                                unfocusedBorderColor = Color.Red
                            ),
                            modifier = Modifier.padding(bottom = 80.dp)
                        )

                        OutlinedTextField(
                            value = uiState.passwordRepeat,
                            onValueChange = { vm.passwordChange(it) },
                            label = { Text(stringResource(R.string.repeat_password)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.active),
                                unfocusedBorderColor = Color.Red
                            ),
                            modifier = Modifier.padding(bottom = 80.dp)
                        )

                        Text(
                            text = "Error: ${uiState.errorMessage}",
                            color = Color.Red,
                            modifier = Modifier.padding(bottom = 40.dp).align(Alignment.CenterHorizontally),
                        )

                        Button(
                            onClick = { vm.signup() },
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
                }

                is SignupUiState.Success -> {
                    // TODO: навигация
                    Text(uiState.successMessage)
                }
            }
        }
    }
}
