package com.yafianwary.juaraandroid.todolistminimalist.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertData(entity: MyEntity)

    @Delete
    fun deleteData(entity: MyEntity)

    @Update
    fun updateData(entity: MyEntity)

    @Query("SELECT * from todo_entity ORDER BY id ASC")
    fun getAllData(): LiveData<List<MyEntity>>
}