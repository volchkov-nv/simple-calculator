package com.example.simplecalculator.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.simplecalculator.presentation.activities.MainActivity
import com.example.simplecalculator.presentation.observers.FragmentObserver
import com.example.simplecalculator.presentation.widgets.menu_panel.MainMenuButtonType

abstract class BaseFragment<VM : BaseViewModel>(layoutResource: Int) : Fragment(layoutResource) {

    abstract val viewModel: VM

    private lateinit var lifecycleObserver: LifecycleObserver

    abstract fun initViewModel()

    abstract fun initUi(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = super.onCreateView(inflater, container, savedInstanceState)
        lifecycleObserver = FragmentObserver{
            initViewModel()
        }
        lifecycle.addObserver(lifecycleObserver)
        return rootView
    }

    override fun onDetach() {
        super.onDetach()
        lifecycle.removeObserver(lifecycleObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(savedInstanceState)
    }

    protected inline fun <reified T : ViewModel> Fragment.injectViewModel(crossinline inject: () -> T): T {
        return ViewModelProvider(this, viewModelFactory(inject))[T::class.java]
    }

    protected fun <T> LiveData<T?>.subscribe(action: (T) -> Unit) {
        this.observe(viewLifecycleOwner, Observer { data -> data?.let { action.invoke(it) } })
    }

    protected fun updateTab(type: MainMenuButtonType) {
        (activity as MainActivity).mainNavigationPanel?.setSelectedTab(type)
    }


}