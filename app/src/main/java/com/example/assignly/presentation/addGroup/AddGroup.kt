package com.example.assignly.presentation.addGroup

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.assignly.R
import com.example.assignly.api.NetworkService
import com.example.assignly.presentation.Navigation
import com.example.assignly.presentation.forms.Form
import com.example.assignly.presentation.forms.ImageForm

@Composable
fun AddGroup(navController: NavController, vm: AddGroupViewModel = viewModel()) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->

        }
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.arrow_left),
            contentDescription = "<",
            modifier = Modifier
                .size(50.dp)
                .clickable { navController.navigate(Navigation.LOGIN.toString()) }
                .weight(1f),
        )

        when (val uiState = vm.uiState.collectAsState().value) {
            is AddGroupUiState.Idle -> {
                Column(modifier = Modifier.weight(4f),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Column(modifier = Modifier.padding(start = 15.dp, end = 15.dp)) {
                        Form(value = uiState.name,
                            label = stringResource(R.string.group_name),
                            isError = false,
                            lambda = { vm.nameChange(it) }
                        )

                        OutlinedTextField(
                            value = vm.membersToString(uiState.members),
                            onValueChange = { vm.membersChange() },
                            label = { Text(stringResource(R.string.members)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.background,
                                focusedTextColor = MaterialTheme.colorScheme.background
                            ),
                            readOnly = true,
                            modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = uiState.description,
                            onValueChange = { vm.descriptionChange(it) },
                            label = { Text(stringResource(R.string.description)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.background,
                            ),
                            modifier = Modifier
                                .padding(bottom = 50.dp)
                                .fillMaxWidth()
                                .heightIn(200.dp)
                        )
                    }
                    ImageForm(uiState.image, text = stringResource(R.string.select_group_image), lambda = { launcher.launch("image/*") })

                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {},
                    contentPadding = PaddingValues(
                        top = 10.dp,
                        bottom = 10.dp,
                        start = 20.dp,
                        end = 20.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text (
                        text = stringResource(R.string.add_group),
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Spacer(modifier = Modifier.weight(0.5f))
            }

            is AddGroupUiState.Loading -> {

            }

            is AddGroupUiState.Error -> {

            }

            is AddGroupUiState.Success -> {
                navController.navigate(Navigation.LOGIN.toString())
            }
        }
    }

}
