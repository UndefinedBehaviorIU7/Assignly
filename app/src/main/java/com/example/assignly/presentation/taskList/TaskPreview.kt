package com.example.assignly.presentation.taskList

import android.content.res.Configuration
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignly.R
import com.example.assignly.ui.theme.AssignlyTheme
import com.example.assignly.ui.theme.Green_
import com.example.assignly.ui.theme.LightGray_

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LightTaskPreview() {
    AssignlyTheme(darkTheme = false, dynamicColor = false) {
        TasksList(taskCount = 3)
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkTaskPreview() {
    AssignlyTheme(darkTheme = true, dynamicColor = false) {
        TasksList(taskCount = 3)
    }
}

@Composable
fun TasksList(taskCount: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 15.dp)
    )
    {
        Column()
        {
            Row(
                verticalAlignment = Alignment.CenterVertically
            )
            {
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(10.dp)
            ) {
                items(taskCount) { index -> TaskPreview(340) }
            }


            Row(modifier = Modifier
                .align(Alignment.End)
                .padding(top = 15.dp, end = 15.dp))
            {
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
fun TaskPreview(size: Int) {
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
                    text = "Placeholder header",
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "descriptionPlaceholder description",
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
                    .padding(4.dp)
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
                        text = "22.08",
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
fun Ring(modifier: Modifier) {
    Canvas(modifier = modifier) {
        drawCircle(
            color = Green_,
            radius = size.minDimension / 2,
            style = Stroke(width = 15f)
        )
    }
}

