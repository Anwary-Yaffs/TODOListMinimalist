package com.yafianwary.juaraandroid.todolistminimalist.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yafianwary.juaraandroid.todolistminimalist.data.MyEntity
import com.yafianwary.juaraandroid.todolistminimalist.data.MyRepository

class MainViewModel(app: Application) : ViewModel() {
    private val myRepository = MyRepository(app)
    fun getAllData(): LiveData<List<MyEntity>> = myRepository.getAllData()

    fun updateData(data: MyEntity) {
        myRepository.updateData(data)
    }
}