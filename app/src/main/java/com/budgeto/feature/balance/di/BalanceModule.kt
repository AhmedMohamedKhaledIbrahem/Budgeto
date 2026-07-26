package com.budgeto.feature.balance.di

import com.budgeto.feature.balance.data.repository.BalanceRepositoryImpl
import com.budgeto.feature.balance.data.service.BalanceLocal
import com.budgeto.feature.balance.data.service.BalanceLocalImpl
import com.budgeto.feature.balance.domain.repository.BalanceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceBalanceModule {
    @Binds
    @Singleton
    abstract fun bindBalanceService(balanceLocalImpl: BalanceLocalImpl): BalanceLocal
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBalanceModule {
    @Binds
    @Singleton
    abstract fun bindBalanceRepository(balanceRepositoryImpl: BalanceRepositoryImpl): BalanceRepository

}