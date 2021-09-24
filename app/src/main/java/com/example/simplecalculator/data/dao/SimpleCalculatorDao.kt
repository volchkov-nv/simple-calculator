package com.example.simplecalculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.simplecalculator.data.entity.HistoryEntity

@Dao
interface SimpleCalculatorDao {

    @Insert
    fun insert(entity: HistoryEntity) : Long

    @Query("SELECT * FROM HistoryEntity")
    fun getAllHistory() : List<HistoryEntity>

    @Query("DELETE FROM HistoryEntity")
    fun deleteAll()

}