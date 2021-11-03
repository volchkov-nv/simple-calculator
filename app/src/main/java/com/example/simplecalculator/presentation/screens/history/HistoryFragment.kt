package com.example.simplecalculator.presentation.screens.history

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplecalculator.R
import com.example.simplecalculator.di.DI
import com.example.simplecalculator.presentation.base.BaseFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.simplecalculator.databinding.HistoryFragmentBinding
import com.example.simplecalculator.domain.models.OperationModel
import com.example.simplecalculator.presentation.activities.MainActivity
import com.example.simplecalculator.presentation.widgets.menu_panel.MainMenuButtonType


class HistoryFragment : BaseFragment<HistoryViewModel>(R.layout.history_fragment) {

    override val viewModel: HistoryViewModel by lazy {
        injectViewModel {
            DI.app.historyViewModel()
        }
    }

    private lateinit var binding : HistoryFragmentBinding
    private val adapter = HistoryListAdapter(mutableListOf(), this::historyItemClickAction)

    private fun historyItemClickAction(model: OperationModel) {
        viewModel.historyItemClick(model)
        findNavController().navigate(HistoryFragmentDirections.actionHistoryClick())
    }

    override fun initViewModel() {
        viewModel.setScreenData.subscribe {
            adapter.updateData(it)
            adapter.notifyDataSetChanged()
            updateEmptyScreen(it.isEmpty())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = HistoryFragmentBinding.bind(view)
        viewModel.getData()
        updateTab(MainMenuButtonType.HISTORY)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initUi(savedInstanceState: Bundle?) {
        binding.historyList.layoutManager = LinearLayoutManager(context)
        binding.historyList.adapter = adapter
        binding.historyList.addItemDecoration(
            DividerItemDecoration(
                binding.historyList.context,
                DividerItemDecoration.VERTICAL
            )
        )
        context?.let {
            val itemTouchHelper = ItemTouchHelper(SwipeCallback(it, this::swipeAction))
            itemTouchHelper.attachToRecyclerView(binding.historyList)
        }
        binding.deleteAll.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle(getString(R.string.dialog_delete_all_history_title))
                .setMessage(getString(R.string.dialog_message_all_history_title))
                .setPositiveButton(R.string.yes, DialogInterface.OnClickListener { _, _ ->
                    viewModel.deleteAllHistory()
                })
                .setNegativeButton(R.string.no, null)
                .setIcon(R.drawable.ic_delete_red)
                .show()
        }
    }

    private fun swipeAction(position : Int) {
        viewModel.deleteItemByPosition(position)
    }

    private fun updateEmptyScreen(isEmpty: Boolean) {
        binding.emptyGrout.isVisible = isEmpty
        binding.historyList.isVisible = !isEmpty
        binding.deleteAll.isVisible = !isEmpty
    }
}