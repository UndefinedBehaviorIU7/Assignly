package com.example.assignly.presentation.taskInfo

import android.app.TaskInfo
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.assignly.R
import com.example.assignly.api.models.Group
import com.example.assignly.presentation.Navigation
import com.example.assignly.ui.theme.Green_
import com.example.assignly.ui.theme.Red_
import com.example.assignly.ui.theme.Yellow_

fun ringColor(status: Int): Color {
    var color: Color = Color.Blue
    when (status) {
        0 -> color = Green_
        1 -> color = Yellow_
        2 -> color = Red_
    }
    return color
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TaskInfo(navController: NavController,
             viewModel: TaskInfoViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is TaskInfoUiState.Error -> {
            Text(
                text = "Retry",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.clickable { viewModel.retry() }
            )
        }
        is TaskInfoUiState.Idle -> {
            val task = (uiState as TaskInfoUiState.Idle).task

            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = "<",
                        modifier = Modifier
                            .size(50.dp)
                            .clickable { navController.navigate(Navigation.GROUP_LIST.toString()) },
                    )
                    Text(text = "Task name")
                    Text(text = (uiState as TaskInfoUiState.Idle).task.name)
                    Text(text = "Task summary")
                    Text(text = (uiState as TaskInfoUiState.Idle).task.summary)
                    Text(text = "Task description")
                    Text(text = (uiState as TaskInfoUiState.Idle).task.description)
                    Text(text = "Members")
                    FlowRow(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        task.members.forEach { item ->
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.onSecondary,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                                    .wrapContentSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item.tag,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                    Row (
                    ) {
                        Text(text = "Status")
                        Ring(modifier = Modifier
                            .size(20.dp)
                            .padding(4.dp),
                            color = ringColor(task.status)
                        )
                    }
                }
            }
        }
        TaskInfoUiState.Loading -> {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun Ring(modifier: Modifier, color: Color) {
    Canvas(modifier = modifier) {
        drawCircle(
            color = color,
            radius = size.minDimension / 2,
            style = Stroke(width = 15f)
        )
    }
}