package com.example.assignly.presentation.signup

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.assignly.R
import com.example.assignly.presentation.login.LoginUiState

@Composable
fun Signup(navController: NavController, vm: SignupViewModel = viewModel()) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            vm.imageChange(uri)
        }
    )

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
                is SignupUiState.Idle, is SignupUiState.Error -> {
                    Column(
                        modifier = Modifier.padding(top = 80.dp)
                    ) {
                        OutlinedTextField(
                            value = when (uiState) {
                                is SignupUiState.Idle -> uiState.login
                                is SignupUiState.Error -> uiState.login
                                else -> ""
                            },
                            onValueChange = { vm.loginChange(it) },
                            label = { Text(stringResource(R.string.login)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.active),
                                unfocusedBorderColor = if (uiState is SignupUiState.Error) Color.Red else Color.Gray
                            ),
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        OutlinedTextField(
                            value = when (uiState) {
                                is SignupUiState.Idle -> uiState.tag
                                is SignupUiState.Error -> uiState.tag
                                else -> ""
                            },
                            onValueChange = { vm.tagChange(it) },
                            label = { Text(stringResource(R.string.tag)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.active),
                                unfocusedBorderColor = if (uiState is SignupUiState.Error) Color.Red else Color.Gray
                            ),
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        OutlinedTextField(
                            value = when (uiState) {
                                is SignupUiState.Idle -> uiState.password
                                is SignupUiState.Error -> uiState.password
                                else -> ""
                            },
                            onValueChange = { vm.passwordChange(it) },
                            label = { Text(stringResource(R.string.password)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.active),
                                unfocusedBorderColor = if (uiState is SignupUiState.Error) Color.Red else Color.Gray
                            ),
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        OutlinedTextField(
                            value = when (uiState) {
                                is SignupUiState.Idle -> uiState.passwordRepeat
                                is SignupUiState.Error -> uiState.passwordRepeat
                                else -> ""
                            },
                            onValueChange = { vm.passwordRepeatChange(it) },
                            label = { Text(stringResource(R.string.repeat_password)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(R.color.active),
                                unfocusedBorderColor = if (uiState is SignupUiState.Error) Color.Red else Color.Gray
                            ),
                            modifier = Modifier.padding(bottom = 20.dp)
                        )

                        val imageUri = when (uiState) {
                            is SignupUiState.Idle -> uiState.image
                            is SignupUiState.Error -> uiState.image
                            else -> ""
                        }

                        Column (
                            modifier = Modifier.padding(bottom = 60.dp)
                        ) {
                            Text(
                                stringResource(R.string.select_image),
                                fontSize = 20.sp,
                                color = Color.DarkGray,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                            Box {
                                if (imageUri != null) {
                                    Image(
                                        painter = rememberAsyncImagePainter(imageUri),
                                        contentDescription = "Selected Image",
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop,
                                        alignment = Alignment.Center
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.placeholder),
                                        contentDescription = "Image Placeholder",
                                        alignment = Alignment.Center,
                                        modifier = Modifier.size(100.dp)
                                            .clickable { launcher.launch("image/*") }
                                    )
                                }
                            }
                        }

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

                is SignupUiState.Success -> {
                    // TODO: навигация
                    Text(uiState.successMessage)
                }
            }

            TextButton(
                onClick = { navController.navigate("login") },
                modifier = Modifier.padding(top = 7.dp)
            ) {
                Text(
                    stringResource(R.string.already_have_an_account),
                    color = colorResource(R.color.active),
                )
            }
        }
    }
}
