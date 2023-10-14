package com.example.interviewproject

import android.app.Application
import com.example.interviewproject.db.MyDatabase

class MyApplication:Application() {

    lateinit var database: MyDatabase
    override fun onCreate() {
        super.onCreate()
        database = MyDatabase.getDatabase(this)
    }
}