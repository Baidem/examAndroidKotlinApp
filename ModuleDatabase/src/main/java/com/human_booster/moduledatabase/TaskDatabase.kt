package com.human_booster.moduledatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.human_booster.moduledatabase.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
