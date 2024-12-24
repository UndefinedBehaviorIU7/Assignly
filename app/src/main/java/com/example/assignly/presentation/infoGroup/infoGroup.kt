package com.example.assignly.presentation.infoGroup

import android.app.Application
import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.swipeable
import com.example.assignly.presentation.taskList.SwipeState
import com.example.assignly.presentation.taskList.TaskPreview
import com.example.assignly.presentation.taskList.TaskUiState
import androidx.compose.material3.Text as Text
import com.example.assignly.presentation.infoGroup.infoGroupUIState
import com.example.assignly.presentation.infoGroup.infoGroupViewModel

@Composable
fun infoGroup(navController: NavController, groupId: Int, token: String) {
    val context = LocalContext.current
    val vm: infoGroupViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return infoGroupViewModel(
                    application = context.applicationContext as Application,
                    groupId = groupId,
                    token = token
                ) as T
            }
        }
    )

    when (val uiState = vm.uiState.collectAsState().value)
    {
        is infoGroupUIState.Idle -> {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxSize()
                    .padding(top = 30.dp)
            ) {
                Column(modifier = Modifier
                    .padding(bottom = 20.dp)
                    .fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()) {
                        Image(
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = "<",
                            modifier = Modifier
                                .size(50.dp)
                                .clickable { navController.navigate(Navigation.GROUP_LIST.toString()) },
                        )
                        Image(
                            painter = painterResource(R.drawable.group_default),
                            contentDescription = "",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(25.dp))
                        )
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = uiState.name,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 20.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(bottom = 20.dp)
                    ) {
                        Text(
                            text = uiState.description,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 50.dp)
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(5.dp),
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.tertiary
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(650.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        items(uiState.members) { user ->
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(20.dp))
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp, horizontal = 10.dp)
                                ,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(25.dp))
                                ) {
                                    Image(
                                        painterResource(R.drawable.group_default),
                                        "",
                                        Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = user.login,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }

            }
        }
        else -> Unit
    }

}

