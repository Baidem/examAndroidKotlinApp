package com.human_booster.examandroidkotlinapp.ui.home

import android.R
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.human_booster.model.Task
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.window.Dialog
import com.human_booster.examandroidkotlinapp.ui.theme.LightBlue

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsState()
    var addTaskDialogOpen by remember { mutableStateOf(false)}
    var editTaskDialogOpen by remember { mutableStateOf(false)}
    var editableTask: Task? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(16.dp),

        ) {
            Text(
                text = "My todo list",
                color = Color.White,
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                )
            OutlinedButton(
                onClick = {
                addTaskDialogOpen = true
                Log.d("addTaskDialogOpen", addTaskDialogOpen.toString())
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
            if (addTaskDialogOpen) {
                editTaskDialogOpen = false
                AddTaskDialog(
                    onSave = { label, description ->
                        viewModel.addTask(label, description)
                    },
                    onDismiss = { addTaskDialogOpen = false })
            }

        }
        Divider(
            color = Color.White,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {

            items(state.tasks) {task ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.DarkGray)
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    TaskListItem(
                        viewModel,
                        task = task,
                        onDeleteTaskClicked = {
                            viewModel.onDeleteTask(task)
                        },
                        onEditTaskClicked = {
                            editTaskDialogOpen = true
                            editableTask = task
                        }
                    )
                    if (editTaskDialogOpen) {
                        addTaskDialogOpen = false
                        editableTask?.let { task ->
                            EditTaskDialog(
                                task,
                                onEdit = { resTask, label, description, status ->
                                    viewModel.editTask(resTask, label, description, status)
                                    editableTask = null
                                },
                                onDismiss = { editTaskDialogOpen = false })
                        }
                    }
                }
            }
        } // LazyColumn
    } // Column
} // end HomeScreen


@Composable
fun TaskListItem(
    viewModel: HomeViewModel,
    task: Task,
    onDeleteTaskClicked: () -> Unit,
    onEditTaskClicked: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),

    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            val (checkbox,text, editButton, deleteButton) = createRefs()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(text){}
            ) {
                Text(
                    text = task.label,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(8.dp)
                )
                Text(
                    text = task.description,
                    modifier = Modifier
                        .padding(8.dp)
                )

            }

            IconButton(
                onClick = onDeleteTaskClicked,
                modifier = Modifier
                    .constrainAs(deleteButton) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete task #${task.id} ${task.label} ?"
                )
            }

            IconButton(
                onClick = onEditTaskClicked,
                modifier = Modifier
                    .constrainAs(editButton) {
                    top.linkTo(parent.top)
                    end.linkTo(deleteButton.start)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit task #${task.id} ${task.label}"
                )
            }
            Checkbox(
                checked = task.status,
                onCheckedChange = { isChecked -> onCheckboxClicked(viewModel, isChecked, task) },
                modifier = Modifier.constrainAs(checkbox) {
                    top.linkTo(parent.top)
                    end.linkTo(editButton.start)
                }
            )
        }
    }
}

fun onCheckboxClicked(viewModel: HomeViewModel,checked: Boolean, task: Task) {
    Log.d("checkbox", checked.toString())
    viewModel.checkTask(task, checked)
}

@Composable
private fun AddTaskDialog(
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
                .height(300.dp),
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
                    inputLabel = "Label",
                    onValueChange = { label = it }
                )
                InputTextField(
                    inputValue = description,
                    inputLabel = "Description",
                    onValueChange = { description = it }
                )
                ToDoButton(text = "Save", onClick = {

                    if (label.trim().isNotEmpty() && description.trim().isNotEmpty()) {
                        onSave(label, description)
                    }
                    onDismiss()
                })
            }
        }
    }
}

@Composable
private fun EditTaskDialog(
    editableTask: Task,
    onEdit: (Task, String, String, Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    var label by remember {
        mutableStateOf(editableTask.label)
    }
    var description by remember {
        mutableStateOf(editableTask.description)
    }
    var status by remember {
        mutableStateOf(editableTask.status)
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .width(400.dp)
                .height(IntrinsicSize.Max),
            border = BorderStroke(1.dp, Color.Black),
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
                    inputLabel = "Label",
                    onValueChange = { label = it }
                )

                InputTextField(
                    inputValue = description,
                    inputLabel = "Description",
                    onValueChange = { description = it }
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = status,
                        onCheckedChange = { status = it },
                    )
                    Text("has been done")

                }

                ToDoButton(text = "Modify", onClick = {
                    if (label.trim().isNotEmpty() && description.trim().isNotEmpty()) {
                        onEdit(editableTask, label, description, status)
                    }
                    onDismiss()
                })
            }
        }
    }
}

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
        onValueChange = onValueChange
    )
}

@Composable
fun ToDoButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {

    Button(
        modifier = modifier.padding(8.dp),
        onClick = onClick,
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Text(text = text, fontWeight = FontWeight.Bold, color = Color.Black)
    }

}