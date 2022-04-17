package com.yafianwary.juaraandroid.todolistminimalist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyEntity::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun myDao(): MyDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MyDatabase {
            if (INSTANCE == null) {
                synchronized(MyDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "todo_database"
                    ).build()
                }
            }
            return INSTANCE as MyDatabase
        }
    }
}