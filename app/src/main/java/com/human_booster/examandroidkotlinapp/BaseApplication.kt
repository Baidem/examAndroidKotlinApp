package com.human_booster.examandroidkotlinapp

import android.app.Application
import com.human_booster.moduletaskrepository.DefaultTaskRepository
import com.human_booster.moduletaskrepository.TaskRepository

class BaseApplication : Application() {

    val taskRepository: TaskRepository by lazy {
        DefaultTaskRepository(this)
    }

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    companion object {
        private var _instance: BaseApplication? = null
        fun getInstance(): BaseApplication = _instance!!
    }

}