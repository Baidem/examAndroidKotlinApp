package com.human_booster.moduletaskrepository

import com.human_booster.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun observeAll(): Flow<List<Task>>

    suspend fun getAll(): List<Task>
    suspend fun add(label: String, description: String)
    suspend fun delete(task: Task)
    suspend fun update(task: Task)
}