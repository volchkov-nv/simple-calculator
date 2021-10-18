package com.example.simplecalculator.di.modules

import com.example.simplecalculator.calculator.SimpleCalculator
import com.example.simplecalculator.calculator.facade.ChainFacade
import com.example.simplecalculator.calculator.facade.ChainFacadeImpl
import com.example.simplecalculator.domain.repos.SimpleCalculatorRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FacadeModule {

    companion object {
        @Provides
        @Singleton
        fun provideChainFacade(
            repository: SimpleCalculatorRepository,
            calculator: SimpleCalculator
        ) : ChainFacade {
            return ChainFacadeImpl(repository, calculator)
        }
    }
}