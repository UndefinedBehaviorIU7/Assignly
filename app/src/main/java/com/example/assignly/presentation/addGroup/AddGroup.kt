package com.example.assignly.presentation.addGroup

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.assignly.R
import com.example.assignly.presentation.Navigation
import com.example.assignly.presentation.forms.Form
import com.example.assignly.presentation.forms.ImageForm

@ExperimentalStdlibApi
@Composable
fun AddGroup(navController: NavController, vm: AddGroupViewModel = viewModel()) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            vm.updateUiState(newImage = uri)
        }
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.arrow_left),
            contentDescription = "<",
            modifier = Modifier
                .size(50.dp)
                .clickable { navController.navigate(Navigation.GROUP_LIST.toString()) }
                .weight(0.7f),
        )

        when (val uiState = vm.uiState.collectAsState().value) {
            is AddGroupUiState.Idle -> {
                Column(modifier = Modifier.weight(4f),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Column(modifier = Modifier.padding(start = 15.dp, end = 15.dp)) {
                        Form(value = uiState.name,
                            label = stringResource(R.string.group_name),
                            isError = false,
                            lambda = { vm.updateUiState(newName = it) }
                        )

                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = vm.membersToString(uiState.members),
                                onValueChange = { vm.updateUiState(newMembers = uiState.members) },
                                label = { Text(stringResource(R.string.members)) },
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
                                        modifier = Modifier.clickable { vm.updateUiState(newMenuExtended = !uiState.menuExpanded) }
                                    )
                                },
                                readOnly = true,
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        uiState.membersFieldPosition= coordinates.size.toSize()
                                    }
                            )

                            DropdownMenu(
                                expanded = uiState.menuExpanded,
                                onDismissRequest = { vm.updateUiState(newMenuExtended = false) },
                                modifier = Modifier
                                    .width(with(LocalDensity.current){uiState.membersFieldPosition.width.toDp()})
                                    .height(300.dp)
                            ) {
                                uiState.allUsers.forEach { user ->
                                    if (user.id != vm.id) {
                                        DropdownMenuItem(
                                            text = { Text(text = user.tag) },
                                            onClick = {
                                                if (user !in uiState.members) {
                                                    uiState.members.add(user)
                                                } else {
                                                    uiState.members.remove(user)
                                                }
                                                vm.updateUiState(newMenuExtended = false)
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        OutlinedTextField(
                            value = uiState.description,
                            onValueChange = { vm.updateUiState(newDescription = it) },
                            label = { Text(stringResource(R.string.description)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.background,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                cursorColor = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier
                                .padding(bottom = 50.dp)
                                .fillMaxWidth()
                                .heightIn(200.dp)
                        )
                    }
                    ImageForm(uiState.image, text = stringResource(R.string.select_group_image), lambda = { launcher.launch("image/*") })

                }

                Spacer(modifier = Modifier.weight(0.5f))

                Button(
                    onClick = { vm.addGroup() },
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

            is AddGroupUiState.Error -> {
                Column(modifier = Modifier.weight(4f),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Column(modifier = Modifier.padding(start = 15.dp, end = 15.dp)) {
                        Form(value = uiState.name,
                            label = stringResource(R.string.group_name),
                            isError = true,
                            lambda = { vm.updateUiState(newName = it) }
                        )

                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = vm.membersToString(uiState.members),
                                onValueChange = { vm.updateUiState(newMembers = uiState.members) },
                                label = { Text(stringResource(R.string.members)) },
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
                                        modifier = Modifier.clickable { vm.updateUiState(newMenuExtended = !uiState.menuExpanded) }
                                    )
                                },
                                readOnly = true,
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        uiState.membersFieldPosition= coordinates.size.toSize()
                                    }
                            )

                            DropdownMenu(
                                expanded = uiState.menuExpanded,
                                onDismissRequest = { vm.updateUiState(newMenuExtended = false) },
                                modifier = Modifier
                                    .width(with(LocalDensity.current){uiState.membersFieldPosition.width.toDp()})
                                    .height(300.dp)
                            ) {
                                uiState.allUsers.forEach { user ->
                                    if (user.id != vm.id) {
                                        DropdownMenuItem(
                                            text = { Text(text = user.tag) },
                                            onClick = {
                                                if (user !in uiState.members) {
                                                    uiState.members.add(user)
                                                } else {
                                                    uiState.members.remove(user)
                                                }
                                                vm.updateUiState(newMenuExtended = false)
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        OutlinedTextField(
                            value = uiState.description,
                            onValueChange = { vm.updateUiState(newDescription = it) },
                            label = { Text(stringResource(R.string.description)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.error,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                cursorColor = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier
                                .padding(bottom = 50.dp)
                                .fillMaxWidth()
                                .heightIn(200.dp)
                        )
                    }
                    ImageForm(uiState.image, text = stringResource(R.string.select_group_image), lambda = { launcher.launch("image/*") })

                }

                Spacer(modifier = Modifier.weight(0.3f))

                Text("Error: ${uiState.errorMessage}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally))

                Spacer(modifier = Modifier.weight(0.5f))

                Button(
                    onClick = { vm.addGroup() },
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
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }

            is AddGroupUiState.Success -> {
                navController.navigate(Navigation.GROUP_LIST.toString())
            }
        }
    }

}
