package com.example.assignly.presentation.taskList

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.assignly.R
import com.example.assignly.api.models.Task
import com.example.assignly.api.models.User
import com.example.assignly.presentation.Navigation
import com.example.assignly.ui.theme.AssignlyTheme
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
//        TasksList()
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

fun deadlineFormat(deadline: String): List<String> {
    val list: List<String> = deadline.split("T")
    val dateList = list[0].split("-")
    val newDate =
        dateList[2] + "." + dateList[1] + "." + dateList[0].slice(dateList[0].length - 2..<dateList[0].length)

    val newList: List<String> = mutableListOf(newDate, list[1])
    return newList
}


@Composable
fun TasksList(
    navController: NavController,
    viewModel: TaskViewModel = viewModel(),
    token: String,
    groupId: Int
) {

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
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { navController.navigate(Navigation.LOGIN.toString()) },
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
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(10.dp)
                        )
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
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(10.dp)
                        )
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
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(10.dp)
                        )
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
                            TaskPreview(task, 380)
                        }
                    }
                }

                TaskUiState.Idle -> {}
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


val user_1: User = User(id = 1, login = "PS", tag = "Snowkey", profileImage = "")
val user_2: User = User(id = 2, login = "Binaar", tag = "Binaar", profileImage = "")
val user_3: User = User(id = 3, login = "ZerG", tag = "ZerG", profileImage = "")
val user_4: User = User(id = 4, login = "WinWinner", tag = "WinWinner", profileImage = "")

val taskPrev: Task = Task(
    id = 1,
    groupId = 21,
    ownerId = 2,
    name = "Сделать Превью",
    summary = "пвпвпвпвп",
    description = "Здесь корочо описание",
    deadline = "2024-20-12T23:03:00",
    status = 1,
    members = mutableListOf<User>(user_1, user_2, user_3, user_4)
)

@Preview(showSystemUi = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LightTaskPreview() {
    AssignlyTheme(darkTheme = false, dynamicColor = false) {
        TaskPreview(task = taskPrev, size = 380)
    }
}

@Preview(showSystemUi = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkTaskPreview() {
    AssignlyTheme(darkTheme = true, dynamicColor = false) {
        TaskPreview(task = taskPrev, size = 380)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TaskPreview(task: Task, size: Int) {
    Box(
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(20.dp))
            .width(size.dp)
            .padding(10.dp),
    ) {
        Box(
            modifier = Modifier
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
                    modifier = Modifier.padding(bottom = 20.dp),
                    text = task.summary,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )


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
                                color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }


        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            Ring(
                modifier = Modifier
                    .size(20.dp)
                    .padding(4.dp),
                color = RingColor(task.status)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .width(70.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(8.dp)
                )
        )
        {
            Row(modifier = Modifier.background(MaterialTheme.colorScheme.primary))
            {

                Column(horizontalAlignment = Alignment.CenterHorizontally)
                {
                    val dateInfo: List<String> = deadlineFormat(task.deadline)

                    Row(verticalAlignment = Alignment.Bottom)
                    {
                        Image(
                            modifier = Modifier.padding(start = 1.dp, bottom = 4.dp),
                            painter = painterResource(R.drawable.calendar),
                            contentDescription = ""
                        )

                        Text(
                            text = dateInfo[1].slice(0..dateInfo[1].length - 4),
                            modifier = Modifier
                                .padding(start = 5.dp),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(start=4.dp, end = 4.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.tertiary
                    )

                    Text(
                        text = dateInfo[0],
                        modifier = Modifier
                            .padding(start = 5.dp, end = 5.dp),
                        fontSize = 14.sp,
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

