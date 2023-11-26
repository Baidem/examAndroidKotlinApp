package com.human_booster.moduletaskrepository

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.human_booster.model.Task
import com.human_booster.moduledatabase.entity.TaskEntity
import com.human_booster.moduledatabase.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DefaultTaskRepository(context: Context) : TaskRepository {

    private val db = Room.databaseBuilder(
        context.applicationContext,
        TaskDatabase::class.java,
        "task_db"
    ).build()

    override fun observeAll(): Flow<List<Task>> {
        Log.d("DefaultTaskRepository", "observeAll")
        return db.taskDao().observeAll().map(::tasksMapper)
    }

    override suspend fun getAll(): List<Task> {
        return db.taskDao().getAll().map(::taskMapper)
    }

    private fun tasksMapper(entities: List<TaskEntity>) : List<Task> =
        entities.map(::taskMapper)

    private fun taskMapper(entity: TaskEntity): Task = Task(
        id = entity.id,
        label = entity.label,
        description = entity.description,
        status = entity.status,
    )

    override suspend fun add(label: String, description: String) {
        withContext(Dispatchers.IO) {
            db.taskDao().insert(
                TaskEntity(
                    label = label,
                    description = description,
                    status = false
                )
            )
        }
    }

    override suspend fun delete(task: Task) {
        withContext(Dispatchers.IO) {
            db.taskDao().deleteTask(task.id)
        }
    }

    override suspend fun update(task: Task) {
        Log.d("DefaultTaskRepository :", "task : " + task.toString())
        withContext(Dispatchers.IO) {
            db.taskDao().updateTask(
                TaskEntity(
                    id= task.id,
                    label = task.label,
                    description = task.description,
                    status = task.status,
                )
            )
        }
    }

}


