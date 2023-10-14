package com.example.interviewproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Data::class], version = 2)
abstract class MyDatabase:RoomDatabase() {

    abstract fun dataDao():DataDao

    companion object{
        private var db:MyDatabase? = null

        fun getDatabase(context: Context):MyDatabase{
            return db?: synchronized(this){
                val instance = Room.databaseBuilder(context,MyDatabase::class.java,"my-database")
                    .fallbackToDestructiveMigration()
                    .build()
                db =instance
                instance
            }
        }
    }
}