package com.human_booster.examandroidkotlinapp.ui.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextField(
    inputValue: String,
    inputLabel: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.White,
        ),
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        value = inputValue,
        label = { Text(text = inputLabel) },
        onValueChange = onValueChange,
        singleLine = true
    )
}
