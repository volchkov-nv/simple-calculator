package com.example.simplecalculator.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simplecalculator.data.dao.SimpleCalculatorDao
import com.example.simplecalculator.data.entity.HistoryEntity

@Database(
    entities = [
        HistoryEntity::class
    ],
    version = 1
)
abstract class CalculatorDataBase : RoomDatabase() {
    abstract fun getSimpleCalculatorDao(): SimpleCalculatorDao
}