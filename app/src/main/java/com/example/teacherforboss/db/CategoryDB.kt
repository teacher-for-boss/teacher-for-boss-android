package com.example.teacherforboss.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.teacherforboss.db.Dao.CategoryDao
import com.example.teacherforboss.db.entity.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1)
abstract class CategoryDB:RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    companion object{

        @Volatile
        private var Instance: CategoryDB?=null

        fun getDatabase(context: Context): CategoryDB {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, CategoryDB::class.java,"CategoryDB")
                    .build()
                    .also { Instance =it }

            }

        }


    }
}