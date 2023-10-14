package com.example.interviewproject.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Data")
data class Data(var name:String,var dob:String,@PrimaryKey()
var id:String) {

}