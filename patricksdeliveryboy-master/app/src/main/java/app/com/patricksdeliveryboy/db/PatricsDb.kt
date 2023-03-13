package com.inmenzo.patrics.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.com.patricksdeliveryboy.models.Data


@Database(entities = [Data::class], version = 1, exportSchema = false)
abstract class PatricsDb : RoomDatabase() {
    abstract fun loginDao(): LoginDao

    companion object {
        @Volatile private var instance: PatricsDb? = null

        fun getDatabase(context: Context): PatricsDb =
                instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
                Room.databaseBuilder(appContext, PatricsDb::class.java, "Patrics.db")
                        .fallbackToDestructiveMigration()
                        .build()
    }
}