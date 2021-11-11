package com.example.simplecalculator.presentation.screens.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplecalculator.R
import com.example.simplecalculator.databinding.HistoryItemBinding
import com.example.simplecalculator.domain.models.OperationModel
import java.lang.StringBuilder

class HistoryListAdapter(
    private var data : MutableList<OperationModel>,
    private val actionClick: (OperationModel) -> Unit
) : RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)
        val binding = HistoryItemBinding.bind(view)

        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.setData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(data : List<OperationModel>) {
        this.data = data.toMutableList()
    }

    inner class HistoryViewHolder(
            private val binding: HistoryItemBinding
        ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener{
                actionClick.invoke(data[adapterPosition])
            }
        }

        fun setData(model: OperationModel) {
            binding.historyText.text = model.getOutputLine()
        }

        private fun OperationModel.getOutputLine() : String {
            return StringBuilder(this.firstValue).append(" ")
                .append(this.operator).append(" ")
                .append(this.secondValue)
                .append(" = ")
                .append(this.result).toString()
        }
    }

}

