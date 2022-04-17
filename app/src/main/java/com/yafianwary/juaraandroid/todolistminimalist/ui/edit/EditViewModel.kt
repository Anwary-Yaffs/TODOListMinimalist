package com.yafianwary.juaraandroid.todolistminimalist.ui.edit

import android.app.Application
import androidx.lifecycle.ViewModel
import com.yafianwary.juaraandroid.todolistminimalist.data.MyEntity
import com.yafianwary.juaraandroid.todolistminimalist.data.MyRepository

class EditViewModel(myApp: Application) : ViewModel() {

    private val myRepository = MyRepository(myApp)

    fun insertData(data: MyEntity) {
        myRepository.insertData(data)
    }

    fun updateData(data: MyEntity) {
        myRepository.updateData(data)
    }

    fun deleteData(data: MyEntity) {
        myRepository.deleteData(data)
    }

}