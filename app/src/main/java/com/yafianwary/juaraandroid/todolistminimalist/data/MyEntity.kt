package com.yafianwary.juaraandroid.todolistminimalist.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "todo_entity")
@Parcelize
data class MyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "due_date")
    var dueDate: String? = null,

    @ColumnInfo(name = "is_done")
    var isDone: Boolean = false
) : Parcelable