package com.human_booster.examandroidkotlinapp.ui.home

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.human_booster.examandroidkotlinapp.BaseApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val taskRepository = BaseApplication.getInstance().taskRepository

    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            taskRepository.observeAll()
                .collect {
                    _state.value = _state.value.copy(
                        tasks = it
                    )
                }
        }
    }

    fun fetchJoke() {
        viewModelScope.launch {
            jokeRepository.fetchJoke()
        }
    }

    fun onDeleteJoke(joke: Joke) {
        viewModelScope.launch {
            jokeRepository.delete(joke)
        }
    }

    fun editJoke(joke: Joke, newJokeValue: String) {
        viewModelScope.launch {
            jokeRepository.update(
                joke.copy(
                    value = newJokeValue
                )
            )
        }
    }

    fun onJokeClicked(joke: Joke) {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(joke.url))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        BaseApplication.getInstance().startActivity(browserIntent)
    }

}
}