package com.example.interviewproject.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DataDao {

    @Insert
    abstract suspend fun insert(data: Data)

    @Update
    abstract suspend fun update(data: Data)

    @Delete
    abstract suspend fun delete(data: Data)

    @Query("Select * from Data")
    abstract fun getDataList(): Flow<List<Data>>

    @Query("Select * from Data where id=:id")
    abstract fun getData(id:String): Flow<Data>

    @Query("Select Exists(Select * from Data where id=:id)")
    abstract suspend fun checkData(id:String):Boolean

    @Query("Update Data SET id=:id,name=:name,dob=:dob where id=:oldId")
    abstract suspend fun updateData(id:String,name:String,dob:String,oldId:String)



}