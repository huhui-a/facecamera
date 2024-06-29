package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "items")
data class item (
    val id:Int,
    val name:String,
    val info:String
)
