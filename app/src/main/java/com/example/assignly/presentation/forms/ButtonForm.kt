package com.example.assignly.presentation.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignly.R

@Composable
fun ButtonForm(modifier: Modifier, buttonText: String, navText: String,
               navigate: () -> Unit, action: () -> Unit) {
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier) {
        Button(
            onClick = action,
            contentPadding = PaddingValues(
                top = 10.dp,
                bottom = 10.dp,
                start = 20.dp,
                end = 20.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.active)
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text (
                text = buttonText,
                fontSize = 25.sp
            )
        }

        TextButton(
            onClick = navigate,
            modifier = Modifier.padding(top = 7.dp)
        ) {
            Text(
                text = navText,
                color = colorResource(R.color.active),
            )
        }
    }
}
