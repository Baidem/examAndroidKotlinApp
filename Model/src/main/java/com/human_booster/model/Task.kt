package com.human_booster.model

import java.util.Date

data class Task(
    val id: Long,
    val label: String,
    val description: String,
    val status: Boolean,
)
