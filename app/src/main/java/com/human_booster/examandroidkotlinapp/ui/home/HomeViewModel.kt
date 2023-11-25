package com.human_booster.examandroidkotlinapp.ui.home

import android.content.ClipDescription
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.human_booster.examandroidkotlinapp.BaseApplication
import com.human_booster.model.Task
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

    fun onAddTask(label: String, description: String) {
        viewModelScope.launch {
            taskRepository.add(label, description)
        }
    }
    fun onDeleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.delete(task)
        }
    }

    fun onEditTask(
        task: Task,
        newLabel: String,
        newDescription: String,
        newStatus: Boolean
    ) {
        viewModelScope.launch {
            taskRepository.update(
                task.copy(
                    label = newLabel,
                    description = newDescription,
                    status = newStatus
                )
            )
        }
    }


}
