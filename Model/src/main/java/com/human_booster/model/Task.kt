package com.human_booster.model

import java.util.Date

data class Task(
    val id: Int,
    val label: String,
    val description: String,
    val status: Boolean,
)
