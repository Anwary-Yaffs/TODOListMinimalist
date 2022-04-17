package com.yafianwary.juaraandroid.todolistminimalist.ui.main

import com.yafianwary.juaraandroid.todolistminimalist.data.MyEntity

interface SetCheckboxStatus {
    fun onItemClicked(myEntity: MyEntity)
}