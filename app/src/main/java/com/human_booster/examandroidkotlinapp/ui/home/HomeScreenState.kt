package com.human_booster.examandroidkotlinapp.ui.home

import com.human_booster.model.Task

data class HomeScreenState(
    val tasks: List<Task> = emptyList(),
    val task: Task? = null

)
