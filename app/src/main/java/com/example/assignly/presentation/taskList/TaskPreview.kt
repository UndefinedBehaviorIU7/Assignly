package com.example.assignly.presentation.taskList

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignly.R
import com.example.assignly.api.models.TasksList
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignly.ui.theme.Green_
import com.example.assignly.ui.theme.Red_
import com.example.assignly.ui.theme.Yellow_

fun RingColor(status: Int): Color {
    var color: Color = Color.Blue
    when (status) {
        0 -> color = Green_
        1 -> color = Yellow_
        2 -> color = Red_
    }
    return color
}


//@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//fun LightTaskPreview() {
//    AssignlyTheme(darkTheme = false, dynamicColor = false) {
//        TasksList(taskCount = 3)
//    }
//}
//
//@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun DarkTaskPreview() {
//    AssignlyTheme(darkTheme = true, dynamicColor = false) {
//        TasksList(taskCount = 3)
//    }
//}

@Composable
fun TasksList(navController: NavController, viewModel: TaskViewModel = viewModel(), token: String, groupId: Int) {

    val limit = 10
    val offset = 0

    LaunchedEffect(Unit) {
        viewModel.fetchTasks(token, groupId, limit, offset)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 30.dp)
    ) {
        Column(modifier = Modifier.padding(bottom = 20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = "<",
                    modifier = Modifier.size(50.dp)
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
                    text = "Undefined Behavior",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 20.sp
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(7.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.tertiary
            )

            Row(modifier = Modifier.padding(top = 5.dp, start = 28.dp, end = 30.dp)) {
                Box(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(10.dp))
                        .padding(top = 7.dp, bottom = 7.dp, start = 20.dp, end = 20.dp)
                ) {
                    Text(
                        text = "All",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(10.dp))
                        .padding(top = 7.dp, bottom = 7.dp, start = 20.dp, end = 20.dp)
                ) {
                    Text(
                        text = "In Progress",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(10.dp))
                        .padding(top = 7.dp, bottom = 7.dp, start = 20.dp, end = 20.dp)
                ) {
                    Text(
                        text = "Done",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 20.sp
                    )
                }
            }

            when (val uiState = viewModel.uiState.collectAsState().value) {
                is TaskUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier)
                }
                is TaskUiState.Error -> {
                    val errorMessage = uiState.message
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is TaskUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(650.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        items(uiState.tasks) { task ->
                            TaskPreview(task, 340)
                        }
                    }
                }

                TaskUiState.Idle -> TODO()
            }

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 15.dp, end = 15.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.plus),
                    contentDescription = "<",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}



@Composable
fun TaskPreview(task: TasksList, size: Int) {
    Box(
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(20.dp))
            .width(size.dp),
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.TopStart)
        ) {
            Column(
                modifier = Modifier
                    .width((size * 2 / 3).dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = task.name,
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = task.summary,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.TopEnd)
        ) {
            Ring(
                modifier = Modifier
                    .size(20.dp)
                    .padding(4.dp),
                color = RingColor(task.status)
            )
        }

        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .fillMaxHeight(),
                shape = RoundedCornerShape(8.dp),
            ) {
                Row(modifier = Modifier.background(MaterialTheme.colorScheme.primary))
                {
                    Image(
                        modifier = Modifier.padding(start = 5.dp, top = 8.dp),
                        painter = painterResource(R.drawable.calendar),
                        contentDescription = ""
                    )
                    Text(
                        text = task.deadline,
                        modifier = Modifier
                            .padding(5.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

            }
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

