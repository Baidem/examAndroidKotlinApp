package com.human_booster.examandroidkotlinapp.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun AddTaskDialog(
    modifier: Modifier = Modifier,
    onSave: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var label by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier
                .width(400.dp)
                .height(IntrinsicSize.Max),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Edit Task",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                InputTextField(
                    inputValue = label,
                    inputLabel = "Label *",
                    onValueChange = { label = it }
                )
                InputTextField(
                    inputValue = description,
                    inputLabel = "Description",
                    onValueChange = { description = it }
                )
                ToDoButton(text = "Save", onClick = {

                    if (label.trim().isNotEmpty()) {
                        onSave(label, description)
                    }
                    onDismiss()
                })
            }
        }
    }
}