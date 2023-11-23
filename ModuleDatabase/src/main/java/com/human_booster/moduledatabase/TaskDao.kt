package com.human_booster.moduledatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.human_booster.moduledatabase.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun observeAll(): Flow<List<TaskEntity>>

    @Insert
    fun insert(taskEntity: TaskEntity)

    @Query("DELETE FROM task")
    fun deleteAll()

    @Query("DELETE FROM task WHERE id = :taskId")
    fun deleteTask(taskId: Int)

    @Update
    fun updateTask(taskEntity: TaskEntity)

}