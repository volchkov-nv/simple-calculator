package com.example.simplecalculator.presentation.widgets.menu_panel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.example.simplecalculator.R
import com.example.simplecalculator.databinding.MainNavigationPanelViewBinding

class MainNavigationPanel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: MainNavigationPanelViewBinding
    private var selectedTab: MainMenuButtonType = MainMenuButtonType.CALCULATOR

    init {
        View.inflate(context, R.layout.main_navigation_panel_view, this)
        binding = MainNavigationPanelViewBinding.inflate(LayoutInflater.from(context), this, true)
        updateView()
    }

    fun setCalcListener(action: () -> Unit) {
        binding.calculator.setOnClickListener{
            action.invoke()
            selectedTab = MainMenuButtonType.CALCULATOR
            updateView()
        }
    }

    fun setHistoryListener(action: () -> Unit) {
        binding.history.setOnClickListener{
            action.invoke()
            selectedTab = MainMenuButtonType.HISTORY
            updateView()
        }
    }

    fun setSettingsListener(action: () -> Unit) {
        binding.settings.setOnClickListener{
            action.invoke()
            selectedTab = MainMenuButtonType.SETTINGS
            updateView()
        }
    }

    private fun updateView() {
        setAllDefaultIcons()
        when(selectedTab) {
            MainMenuButtonType.CALCULATOR -> binding.calculator.setImageResource(R.drawable.ic_calculator_selected)
            MainMenuButtonType.HISTORY -> binding.history.setImageResource(R.drawable.ic_history_selected)
            MainMenuButtonType.SETTINGS -> binding.settings.setImageResource(R.drawable.ic_settings_selected)
        }
    }

    private fun setAllDefaultIcons() {
        binding.calculator.setImageResource(R.drawable.ic_calculator)
        binding.history.setImageResource(R.drawable.ic_history)
        binding.settings.setImageResource(R.drawable.ic_settings)
    }
}