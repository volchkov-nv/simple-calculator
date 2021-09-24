package com.example.simplecalculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.simplecalculator.data.entity.HistoryEntity

@Dao
interface SimpleCalculatorDao {

    @Insert
    fun insert(entity: HistoryEntity)


}