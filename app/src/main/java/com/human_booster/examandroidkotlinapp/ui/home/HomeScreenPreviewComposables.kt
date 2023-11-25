package com.human_booster.examandroidkotlinapp.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.human_booster.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreenPreview() {
    val tasks = listOf(
        Task(id = 1L, label = "preview", description = "description preview", status = false),
        Task(id = 2L, label = "preview", description = "description preview", status = false)
    )


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        var editableTask: Task = Task(id = 1L, label = "preview", description = "description preview", status = false)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(vertical = 25.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {Text(text = "ADD TASK")}
                        }
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(vertical = 25.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {Text(text = "ADD TASK")}
                        }
                    }
                    Text(text = "ADD TASK")
                    Button(
                        onClick = {

                        }
                    ) {
                        Text(text = "ADD TASK")
                    }
                }
            }

            items(tasks) { task ->
                TaskListItem(
                    task = task,
                    onJokeClicked = {

                    },
                    onDeleteJokeClicked = {

                    },
                    onEditJokeClicked = {

                    }
                )
            }
        }

        editableTask?.let { task ->
            var taskLabel by remember { mutableStateOf(task.label) }
            AlertDialog(
                onDismissRequest = {

                },
                confirmButton = {
                    TextButton(onClick = {

                    }) {
                        Text(text = "Save")
                    }
                },
                text = {
                    TextField(value = taskLabel, onValueChange = {
                        taskLabel = it
                    })
                }
            )
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListItem(
    task: Task,
    onJokeClicked: () -> Unit,
    onDeleteJokeClicked: () -> Unit,
    onEditJokeClicked: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            onJokeClicked()
        }
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val (text, deleteButton, editButton) = createRefs()

            Text(
                text = task.label,
                modifier = Modifier
                    .border(width = 2.dp, color = Color.Green)
                    .constrainAs(text) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(editButton.start)
                        bottom.linkTo(parent.bottom)
                    }
            )

            IconButton(
                onClick = onDeleteJokeClicked,
                modifier = Modifier.constrainAs(deleteButton) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete task ${task.id}"
                )
            }

            IconButton(
                onClick = onEditJokeClicked,
                modifier = Modifier.constrainAs(editButton) {
                    top.linkTo(parent.top)
                    end.linkTo(deleteButton.start)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit task ${task.id}"
                )
            }
        }
    }
}
