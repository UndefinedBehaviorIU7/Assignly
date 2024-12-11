package com.example.assignly.presentation.signup

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.assignly.R
import com.example.assignly.presentation.Navigation
import com.example.assignly.presentation.forms.ButtonForm
import com.example.assignly.presentation.forms.Form
import com.example.assignly.presentation.forms.ImageForm

@Composable
fun Signup(navController: NavController, vm: SignupViewModel = viewModel()) {
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
            Spacer(modifier = Modifier.weight(0.5f))

            Text(
                stringResource(R.string.app_name),
                fontSize = 40.sp,
                modifier = Modifier.weight(0.5f)
            )
            when (val uiState = vm.uiState.collectAsState().value) {
                is SignupUiState.Idle -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(2.5f).fillMaxWidth()
                    ) {
                        Form(uiState.login, label = stringResource(R.string.login),
                            isError = false, lambda = {vm.loginChange(it)})

                        Form(uiState.tag, label = stringResource(R.string.tag),
                            isError = false, lambda = {vm.tagChange(it)})

                        Form(uiState.password, label = stringResource(R.string.password),
                            isError = false, lambda = {vm.passwordChange(it)})

                        Form(uiState.passwordRepeat, label = stringResource(R.string.repeat_password),
                            isError = false, lambda = {vm.passwordRepeatChange(it)})

                        ImageForm(uiState.image) { launcher.launch("image/*") }

                        Spacer(modifier = Modifier.height(60.dp))
                    }
                }

                is SignupUiState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(2.5f).fillMaxWidth()
                    ) {
                        Form(uiState.login, label = stringResource(R.string.login),
                            isError = true, lambda = {vm.loginChange(it)})

                        Form(uiState.tag, label = stringResource(R.string.tag),
                            isError = true, lambda = {vm.tagChange(it)})

                        Form(uiState.password, label = stringResource(R.string.password),
                            isError = true, lambda = {vm.passwordChange(it)})

                        Form(uiState.passwordRepeat, label = stringResource(R.string.repeat_password),
                            isError = true, lambda = {vm.passwordRepeatChange(it)})

                        ImageForm(uiState.image) { launcher.launch("image/*") }

                        Spacer(modifier = Modifier.height(35.dp))
                        Text(
                            text = "Error: ${uiState.errorMessage}",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        )
                        Spacer(modifier = Modifier.height(35.dp))
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

                is SignupUiState.Auth -> {
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

            ButtonForm(modifier = Modifier.weight(0.5f), buttonText = stringResource(R.string.signup),
                navText = stringResource(R.string.already_have_an_account), action = {vm.signup()},
                navigate = { navController.navigate(Navigation.LOGIN.toString()) })

            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}
