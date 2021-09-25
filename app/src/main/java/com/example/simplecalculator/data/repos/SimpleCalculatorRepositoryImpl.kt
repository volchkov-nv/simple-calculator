package com.example.simplecalculator.data.repos


import android.content.Context
import com.example.simplecalculator.data.converters.CalculatorConverter
import com.example.simplecalculator.data.database.CalculatorDataBase
import com.example.simplecalculator.domain.models.HistoryModel
import com.example.simplecalculator.domain.repos.SimpleCalculatorRepository
import kotlinx.coroutines.*
import javax.inject.Inject

@DelicateCoroutinesApi
class SimpleCalculatorRepositoryImpl @Inject constructor(
    private val database: CalculatorDataBase
) : SimpleCalculatorRepository {

    private val dao = database.getSimpleCalculatorDao()

    override fun getAllHistoryData(onSuccess: (List<HistoryModel>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val data = dao.getAllHistory().map {
                CalculatorConverter.convertHistoryEntityToModel(it)
            }
            withContext(Dispatchers.Main) {
                onSuccess.invoke(data)
            }
        }
    }

    override fun  setNewHistoryData(
        model: HistoryModel,
        onSuccess: (Long) -> Unit,
        onError: () -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val id = dao.insert(CalculatorConverter.convertHistoryModelToEntity(model))
            withContext(Dispatchers.Main) {
                onSuccess.invoke(id)
            }
        }
    }

    override fun clearAllHistory() {
        GlobalScope.launch(Dispatchers.IO) {
            dao.deleteAll()
        }
    }
}