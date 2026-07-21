package com.budgeto.feature.spendingmoney.di

import com.budgeto.feature.spendingmoney.data.repository.SpendingMoneyRepositoryImpl
import com.budgeto.feature.spendingmoney.data.service.SpendingMoneyLocal
import com.budgeto.feature.spendingmoney.data.service.SpendingMoneyLocalImpl
import com.budgeto.feature.spendingmoney.domain.repository.SpendingMoneyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SpendingMoneyModule {
    @Binds
    @Singleton
    abstract fun bindSpendingMoneyLocal(
        spendingMoneyLocalImpl: SpendingMoneyLocalImpl
    ): SpendingMoneyLocal

    @Binds
    @Singleton
    abstract fun bindSpendingMoneyRepository(
        spendingMoneyRepositoryImpl: SpendingMoneyRepositoryImpl
    ) : SpendingMoneyRepository
}