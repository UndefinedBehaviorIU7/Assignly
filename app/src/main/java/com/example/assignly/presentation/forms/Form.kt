package com.example.assignly.presentation.forms

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.util.Size
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.assignly.R
import java.util.Calendar
import androidx.compose.ui.unit.dp
import com.example.assignly.api.models.User

@Composable
fun Form (value: String, label: String, isError: Boolean, lambda: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = lambda,
        label = { Text(label) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.background,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedLabelColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.onSurface
        ),
        readOnly = false,
        modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth()
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun FormAddData( value: String, label: String, isError: Boolean, lambda: (String) -> Unit ) {
    var showDatePicker by remember { mutableStateOf(false) }
    var formattedValue by remember { mutableStateOf(value) }

    val textFieldState = remember { mutableStateOf(TextFieldValue(formattedValue)) }

    val onValueChange: (String) -> Unit = { input ->
        val digitsOnly = input.replace(Regex("\\D"), "")

        val chunks = digitsOnly.chunked(2)
        val formattedDate = when {
            digitsOnly.length <= 2 -> digitsOnly
            digitsOnly.length <= 4 -> chunks.joinToString(".")
            digitsOnly.length <= 8 -> chunks[0] + "." + chunks[1] + "." + digitsOnly.substring(4)
            else -> chunks[0] + "." + chunks[1] + "." + digitsOnly.substring(4).take(4)
        }

        formattedValue = formattedDate
        lambda(formattedDate)

        textFieldState.value = TextFieldValue(formattedDate, selection = TextRange(formattedDate.length, formattedDate.length))

    }


    OutlinedTextField(
        value = textFieldState.value,
        onValueChange = { newValue ->
            onValueChange(newValue.text)
        },
        label = { Text(label) },
        readOnly = false,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.background,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedLabelColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(painterResource(R.drawable.calendar), contentDescription = "Выбрать дату")
            }
        }
    )

    if (showDatePicker) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            LocalContext.current,
            { _, year, month, dayOfMonth ->
                val formattedDate = String.format("%02d.%02d.%d", dayOfMonth, month + 1, year)
                lambda(formattedDate)
                onValueChange(formattedDate)
                showDatePicker = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun FormAddTime(value: String, label: String, isError: Boolean,
    lambda: (String) -> Unit
) {
    var showTimePicker by remember { mutableStateOf(false) }
    var formattedValue by remember { mutableStateOf(value) }
    val textFieldState = remember { mutableStateOf(TextFieldValue(formattedValue)) }

    val onValueChange: (String) -> Unit = { input ->
        val digitsOnly: String = input.replace(Regex("\\D"), "")

        val chunks = digitsOnly.chunked(2)
        val formattedTime = when {
            digitsOnly.length <= 2 -> digitsOnly
            digitsOnly.length <= 4 -> chunks.joinToString(":")
            else -> chunks[0] + ":" + chunks[1]
        }

        formattedValue = formattedTime
        lambda(formattedTime)

        textFieldState.value = TextFieldValue(formattedTime, selection = TextRange(formattedTime.length, formattedTime.length))
    }

    OutlinedTextField(
        value = textFieldState.value,
        onValueChange = { newValue ->
            onValueChange(newValue.text)
        },
        label = { Text(label) },
        readOnly = false,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.background,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedLabelColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { showTimePicker = true }) {
                Icon(Icons.Default.DateRange, contentDescription = "Выбрать время")
            }
        }
    )

    if (showTimePicker) {
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            LocalContext.current,
            { _, hourOfDay, minute ->
                val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                lambda(formattedTime)
                onValueChange(formattedTime)
                showTimePicker = false
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }
}

//@Composable
//fun FormMembers(
//    value: String,
//    isMenuExpanded: Boolean,
//    onMenuToggle: () -> Unit,
//    onDismissMenu: () -> Unit,
//    allUsers: List<User>,
//    onUserClick: (User) -> Unit
//) {
//    Box(modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth()) {
//        OutlinedTextField(
//            value = value,
//            onValueChange = {}, // Поле read-only, изменений не требуется
//            label = { Text("members") },
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
//                unfocusedBorderColor = MaterialTheme.colorScheme.background,
//                focusedTextColor = MaterialTheme.colorScheme.onSurface,
//                focusedLabelColor = MaterialTheme.colorScheme.onSurface,
//                cursorColor = MaterialTheme.colorScheme.onSurface
//            ),
//            trailingIcon = {
//                Icon(
//                    imageVector = if (isMenuExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
//                    contentDescription = null,
//                    modifier = Modifier.clickable { onMenuToggle() }
//                )
//            },
//            readOnly = true,
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        DropdownMenu(
//            expanded = isMenuExpanded,
//            onDismissRequest = { onDismissMenu() }
//        ) {
//            allUsers.forEach { user ->
//                DropdownMenuItem(
//                    text = { Text(user.tag) },
//                    onClick = { onUserClick(user) }
//                )
//            }
//        }
//    }
//}



//@Composable
//fun FormInt (value: Int, label: String, isError: Boolean, lambda: (Int) -> Unit) {
//    OutlinedTextField(
//        value = value.toString(),
//        onValueChange = lambda,
//        label = { Text(label) },
//        colors = OutlinedTextFieldDefaults.colors(
//            focusedBorderColor = colorResource(R.color.active),
//            unfocusedBorderColor = if (isError) Color.Red else Color.Gray
//        ),
//        modifier = Modifier.padding(bottom = 10.dp)
//    )
//}


