package com.example.assignly.presentation.forms

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.assignly.R

@Composable
fun Form (value: String, label: String, isError: Boolean, lambda: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = lambda,
        label = { Text(label) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(R.color.active),
            unfocusedBorderColor = if (isError) Color.Red else Color.Gray
        ),
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
fun FormInt (value: Int, label: String, isError: Boolean, lambda: (String) -> Unit) {
    OutlinedTextField(
        value = value.toString(),
        onValueChange = lambda,
        label = { Text(label) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(R.color.active),
            unfocusedBorderColor = if (isError) Color.Red else Color.Gray
        ),
        modifier = Modifier.padding(bottom = 10.dp)
    )
}
