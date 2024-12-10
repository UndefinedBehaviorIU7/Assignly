package com.example.assignly.presentation.login

import android.annotation.SuppressLint
import android.graphics.ImageDecoder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.assignly.R

@SuppressLint("ResourceType")
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LogoPreview() {
    Icon(
        painter = painterResource(id = R.drawable.assignly_text),
        modifier = Modifier.size(300.dp),
        contentDescription = "",
        tint = Color.Black
    )
}

@Composable
fun Login(navController: NavController, vm: LoginViewModel = viewModel()) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.assignly_text),
                modifier = Modifier.size(280.dp),
                contentDescription = ""
            )

            when (val uiState = vm.uiState.collectAsState().value) {
                is LoginUiState.Idle, is LoginUiState.Error -> {
                    Column(
                        modifier = Modifier.weight(1.2f)
                    ) {
                        OutlinedTextField(
                            value = when (uiState) {
                                is LoginUiState.Idle -> uiState.login
                                is LoginUiState.Error -> uiState.login
                                else -> ""
                            },
                            onValueChange = { vm.loginChange(it) },
                            label = { Text(stringResource(R.string.login)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.active),
                                unfocusedBorderColor = if (uiState is LoginUiState.Error) Color.Red else Color.Gray
                            ),
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        OutlinedTextField(
                            value = when (uiState) {
                                is LoginUiState.Idle -> uiState.password
                                is LoginUiState.Error -> uiState.password
                                else -> ""
                            },
                            onValueChange = { vm.passwordChange(it) },
                            label = { Text(stringResource(R.string.password)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.active),
                                unfocusedBorderColor = if (uiState is LoginUiState.Error) Color.Red else Color.Gray
                            ),
                        )

                        if (uiState is LoginUiState.Error) {
                            Spacer(modifier = Modifier.height(35.dp))
                            Text(
                                text = "Error: ${uiState.errorMessage}",
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            )
                            Spacer(modifier = Modifier.height(35.dp))
                        } else {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }

                is LoginUiState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is LoginUiState.Success -> {

                    // TODO: Навигация
                    Text(uiState.successMessage)
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Button(
                    onClick = { vm.auth() },
                    contentPadding = PaddingValues(
                        top = 10.dp,
                        bottom = 10.dp,
                        start = 20.dp,
                        end = 20.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.active)
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        fontSize = 25.sp
                    )
                }

                TextButton(
                    onClick = { navController.navigate("signup") },
                    modifier = Modifier.padding(top = 7.dp)
                ) {
                    Text(
                        stringResource(R.string.create_new_account),
                        color = colorResource(R.color.active),
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
