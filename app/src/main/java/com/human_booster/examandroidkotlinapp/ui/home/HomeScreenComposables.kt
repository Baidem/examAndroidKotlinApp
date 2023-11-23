package com.human_booster.examandroidkotlinapp.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        var editableJoke: Joke? by remember { mutableStateOf(null) }

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
                    Button(
                        onClick = {
                            viewModel.fetchJoke()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.fetch_joke))
                    }
                }
            }

            items(state.jokes) { joke ->
                JokeListItem(
                    joke = joke,
                    onJokeClicked = {
                        viewModel.onJokeClicked(joke)
                    },
                    onDeleteJokeClicked = {
                        viewModel.onDeleteJoke(joke)
                    },
                    onEditJokeClicked = {
                        editableJoke = joke
                    }
                )
            }
        }

        editableJoke?.let { joke ->
            var jokeValue by remember { mutableStateOf(joke.value) }
            AlertDialog(
                onDismissRequest = {
                    editableJoke = null
                },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.editJoke(joke, jokeValue)
                        editableJoke = null
                    }) {
                        Text(text = "Save")
                    }
                },
                text = {
                    TextField(value = jokeValue, onValueChange = {
                        jokeValue = it
                    })
                }
            )
        }

//        val joke = state.joke
//        if (joke == null) {
//            Button(onClick = {
//                viewModel.getSingleJoke()
//            }) {
//                Text(text = stringResource(id = R.string.fetch_joke))
//            }
//        } else {
//            Text(text = joke.value)
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokeListItem(
    joke: Joke,
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
                text = joke.value,
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
                    contentDescription = "Delete joke ${joke.id}"
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
                    contentDescription = "Edit joke ${joke.id}"
                )
            }
        }
    }
}