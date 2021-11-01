package com.example.simplecalculator.data.repos


import android.content.Context
import com.example.simplecalculator.data.converters.CalculatorConverter
import com.example.simplecalculator.data.database.CalculatorDataBase
import com.example.simplecalculator.domain.models.OperationModel
import com.example.simplecalculator.domain.repos.SimpleCalculatorRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class SimpleCalculatorRepositoryImpl @Inject constructor(
    private val context: Context,
    private val database: CalculatorDataBase
) : SimpleCalculatorRepository {

    private val dao = database.getSimpleCalculatorDao()
    private var state = OperationModel.getNew()

    override fun getCurrentState()  = state

    override fun updateCurrentState(newState : OperationModel) {
        state = newState
    }

    override fun getAllHistoryData(
        onSuccess: (List<OperationModel>) -> Unit,
        scope: CoroutineScope
    ) {
        scope.launch(Dispatchers.IO) {
            val data = dao.getAllHistory().map {
                CalculatorConverter.convertHistoryEntityToModel(it)
            }
            withContext(Dispatchers.Main) {
                onSuccess.invoke(data)
            }
        }
    }

    override fun  setNewHistoryData(
        model: OperationModel,
        onSuccess: (Long) -> Unit,
        onError: () -> Unit,
        scope: CoroutineScope
    ) {
        scope.launch(Dispatchers.IO) {
            val id = dao.insert(CalculatorConverter.convertOperationModelToEntity(model))
            withContext(Dispatchers.Main) {
                onSuccess.invoke(id)
            }
        }
    }

    override fun clearAllHistory(scope: CoroutineScope) {
        scope.launch(Dispatchers.IO) {
            dao.deleteAll()
        }
    }
}