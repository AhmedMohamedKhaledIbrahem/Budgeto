package com.budgeto.feature.balance.di

import com.budgeto.feature.balance.data.notifier.BalanceAlertNotifierImpl
import com.budgeto.feature.balance.data.repository.BalanceAlertStateRepositoryImpl
import com.budgeto.feature.balance.data.repository.BalanceRepositoryImpl
import com.budgeto.feature.balance.data.service.BalanceAlertStateLocal
import com.budgeto.feature.balance.data.service.BalanceAlertStateLocalImpl
import com.budgeto.feature.balance.data.service.BalanceLocal
import com.budgeto.feature.balance.data.service.BalanceLocalImpl
import com.budgeto.feature.balance.domain.notifier.BalanceAlertNotifier
import com.budgeto.feature.balance.domain.repository.BalanceAlertStateRepository
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

    @Binds
    @Singleton
    abstract fun bindBalanceAlertStateService(impl: BalanceAlertStateLocalImpl): BalanceAlertStateLocal
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBalanceModule {
    @Binds
    @Singleton
    abstract fun bindBalanceRepository(balanceRepositoryImpl: BalanceRepositoryImpl): BalanceRepository

    @Binds
    @Singleton
    abstract fun bindBalanceAlertStateRepository(impl: BalanceAlertStateRepositoryImpl): BalanceAlertStateRepository

}

@Module
@InstallIn(SingletonComponent::class)
abstract class NotifierBalanceModule {
    @Binds
    @Singleton
    abstract fun bindBalanceAlertNotifier(impl: BalanceAlertNotifierImpl): BalanceAlertNotifier
}