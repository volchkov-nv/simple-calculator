package com.example.simplecalculator.presentation.screens.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplecalculator.domain.models.OperationModel
import com.example.simplecalculator.domain.repos.SimpleCalculatorRepository
import com.example.simplecalculator.presentation.base.BaseViewModel
import com.example.simplecalculator.presentation.base.onNext
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val repository: SimpleCalculatorRepository
) : BaseViewModel() {

    val setScreenData = MutableLiveData<List<OperationModel>>()

    fun getData() {
        repository.getAllHistoryData(
            this::receiveData,
            viewModelScope
        )
    }

    private fun receiveData(list: List<OperationModel>) {
        setScreenData.onNext(list.reversed())
    }

    private fun deleteItemFromDb(id: String) : Boolean {
        return if (id.isNotEmpty()) {
            try {
                repository.clearHistoryById(id.toLong(), viewModelScope)
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        } else {
            false
        }
    }

    fun deleteItemByPosition(position : Int) {
        val data = setScreenData.value
        if (data != null && data.size >= position) {
            val item = data[position]
            if (deleteItemFromDb(item.id)) {
                val list = data.toMutableList()
                list.removeAt(position)
                setScreenData.onNext(list.toList())
                return
            }
        }
        setScreenData.onNext(listOf())
    }

    fun deleteAllHistory() {
        repository.clearAllHistory(viewModelScope)
        setScreenData.onNext(listOf())
    }

    fun historyItemClick(model: OperationModel) {
        repository.updateCurrentState(model)
    }
}