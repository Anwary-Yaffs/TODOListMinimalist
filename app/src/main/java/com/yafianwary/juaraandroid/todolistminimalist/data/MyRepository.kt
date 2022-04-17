package com.yafianwary.juaraandroid.todolistminimalist.data

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.Executors

class MyRepository(application: Application) {
    private val myDao: MyDao
    private val dbService = Executors.newSingleThreadExecutor()

    init {
        val database = MyDatabase.getDatabase(application)
        myDao = database.myDao()
    }

    fun getAllData(): LiveData<List<MyEntity>> = myDao.getAllData()

    fun insertData(data: MyEntity) {
        dbService.execute { myDao.insertData(data) }
    }

    fun deleteData(data: MyEntity) {
        dbService.execute { myDao.deleteData(data) }
    }

    fun updateData(data: MyEntity) {
        dbService.execute { myDao.updateData(data) }
    }
}