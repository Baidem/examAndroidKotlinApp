package com.human_booster.examandroidkotlinapp.ui.home

import android.R
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
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
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import com.human_booster.model.Task
import com.human_booster.examandroidkotlinapp.ui.composable.AddTaskDialog
import com.human_booster.examandroidkotlinapp.ui.composable.EditTaskDialog
import com.human_booster.examandroidkotlinapp.ui.composable.TaskListItem

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
        }
    }
}
