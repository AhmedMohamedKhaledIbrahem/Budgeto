package com.budgeto.core.database.di

import android.content.Context
import androidx.room.Room
import com.budgeto.core.database.BudgetoDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BudgetoDatabaseModule {

    @Provides
    @Singleton
    fun provideBudgetoDatabase(
        @ApplicationContext context: Context
    ): BudgetoDataBase {
        return Room.databaseBuilder(
            context = context,
            klass = BudgetoDataBase::class.java,
            name = "budgeto_database"
        ).fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideMonthlyBudgetDao(
        budgetoDataBase: BudgetoDataBase
    ) = budgetoDataBase.monthlyBudgetDao()

    @Provides
    @Singleton
    fun provideSpendingDao(
        budgetoDataBase: BudgetoDataBase
    ) = budgetoDataBase.spendingDao()

}