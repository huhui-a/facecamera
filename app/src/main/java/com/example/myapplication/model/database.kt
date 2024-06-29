package com.example.myapplication.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [item::class], version = 1)
abstract class database: RoomDatabase() {
    abstract fun itemDao()
    companion object{
        @Volatile
        private var Instance:database?=null
        fun getdatabase(context: Context):database{
            return Instance?: synchronized(this){
                Room.databaseBuilder(context,database::class.java,"infomations")
                    .build().also { Instance=it }
            }
        }
    }
}