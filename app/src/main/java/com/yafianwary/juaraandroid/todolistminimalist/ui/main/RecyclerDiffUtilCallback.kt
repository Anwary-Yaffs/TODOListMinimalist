package com.yafianwary.juaraandroid.todolistminimalist.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.yafianwary.juaraandroid.todolistminimalist.data.MyEntity

class RecyclerDiffUtilCallback(private val mOldDataList: List<MyEntity>, private val mNewDataList: List<MyEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldDataList.size
    }

    override fun getNewListSize(): Int {
        return mNewDataList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldDataList[oldItemPosition].id == mNewDataList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldDataList = mOldDataList[oldItemPosition]
        val newDataList = mNewDataList[newItemPosition]
        return oldDataList == newDataList
    }
}