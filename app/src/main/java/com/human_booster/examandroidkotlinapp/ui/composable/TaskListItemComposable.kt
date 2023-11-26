package com.human_booster.examandroidkotlinapp.ui.composable

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.human_booster.examandroidkotlinapp.ui.home.HomeViewModel
import com.human_booster.model.Task

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

private fun onCheckboxClicked(viewModel: HomeViewModel,checked: Boolean, task: Task) {
    Log.d("checkbox", checked.toString())
    viewModel.checkTask(task, checked)
}