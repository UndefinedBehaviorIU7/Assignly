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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignly.R


@Preview(showSystemUi = true)
@Composable
fun taskPreview_() {
    tasksList(3)
}

@Composable
fun tasksList(taskCount: Int) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(10.dp)
    ) {
        items(taskCount) { index -> taskPreview(340) }
    }
}


@Composable
fun taskPreview(size: Int) {
    Box(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(20.dp))
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
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "descriptionPlaceholder description",
                    fontSize = 15.sp,
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
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(10.dp))
                    .fillMaxHeight(),
                shape = RoundedCornerShape(8.dp),
            ) {
                Row()
                {
                    Image(painter = painterResource(R.drawable.calendar), contentDescription = "", Modifier.padding(5.dp))
                    Text(
                        text = "22.08",
                        modifier = Modifier
                            .padding(5.dp)
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
            color = Color.Green,
            radius = size.minDimension / 2,
            style = Stroke(width = 15f)
        )
    }
}

