package com.human_booster.examandroidkotlinapp

import android.app.Application

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