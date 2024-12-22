package com.example.assignly.presentation.forms

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.assignly.R
import java.util.Calendar


//@Composable
//fun FormAddData (value: String, label: String, isError: Boolean, lambda: (String) -> Unit) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = lambda,
//        label = { Text(label) },
//        colors = OutlinedTextFieldDefaults.colors(
//            focusedBorderColor = colorResource(R.color.active),
//            unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.primary else Color.Gray,
//        ),
//        modifier = Modifier.padding(bottom = 10.dp)
//    )
//}





