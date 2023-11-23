package com.human_booster.moduledatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    val label: String,
    val description: String,
    val status: Boolean,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}