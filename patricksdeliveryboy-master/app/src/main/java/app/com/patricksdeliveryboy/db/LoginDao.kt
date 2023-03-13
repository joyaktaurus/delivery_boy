package com.inmenzo.patrics.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.com.patricksdeliveryboy.models.Data

@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(data: Data) : Long

    @Query("select * from User limit 1")
    fun getUser() : LiveData<Data>

    @Query("delete from User")
    suspend fun nukeTable(): Int
}