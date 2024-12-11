package com.example.assignly.presentation.logo

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.assignly.R

@SuppressLint("ResourceType")
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LogoPreview() {
    Icon(
        painter = painterResource(id = R.drawable.assignly_text),
        modifier = Modifier.size(300.dp),
        contentDescription = "",
        tint = Color.Black
    )
}
