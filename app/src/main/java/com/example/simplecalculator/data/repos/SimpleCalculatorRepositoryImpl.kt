package com.example.simplecalculator.data.repos


import android.content.Context
import com.example.simplecalculator.data.converters.CalculatorConverter
import com.example.simplecalculator.data.database.CalculatorDataBase
import com.example.simplecalculator.domain.models.HistoryModel
import com.example.simplecalculator.domain.repos.SimpleCalculatorRepository
import javax.inject.Inject

class SimpleCalculatorRepositoryImpl @Inject constructor(
    private val context: Context,
    private val database: CalculatorDataBase
) : SimpleCalculatorRepository {

    private val dao = database.getSimpleCalculatorDao()

    override fun getAllHistoryData() : List<HistoryModel> {
        return dao.getAllHistory().map {
            CalculatorConverter.convertHistoryEntityToModel(it)
        }
    }

    override fun setNewHistoryData(model: HistoryModel) : Long {
        return dao.insert(CalculatorConverter.convertHistoryModelToEntity(model))
    }

    override fun clearAllHistory() {
        dao.deleteAll()
    }
}