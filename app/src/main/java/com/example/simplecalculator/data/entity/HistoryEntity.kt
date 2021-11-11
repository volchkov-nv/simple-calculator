package com.example.simplecalculator.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Long?,
    @ColumnInfo(name = "first_value")
    val firstValue: String,
    @ColumnInfo(name = "second_value")
    val secondValue: String,
    val operator: String,
    val result: String
)

    
