package com.budgeto.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.budgeto.core.database.entity.SpendingEntity

@Dao
interface SpendingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpending(spending: SpendingEntity)

    @Query(
        """
        select coalesce(sum(amountCents),0)
        from spending_table where date BETWEEN :startDate AND :endDate
    """
    )
    suspend fun getTotalSpendingByMonth(startDate: Long, endDate: Long): Long

    @Query(
        """
        select * from spending_table
        where date like :month || '%'
    """
    )
    suspend fun getSpendingByMonth(month: String): List<SpendingEntity>

    @Query(
        """
        select * from spending_table
        where category = :category
    """
    )
    suspend fun getSpendingByCategory(category: String): List<SpendingEntity>

    @Query(
        """
        select * from spending_table
    """
    )
    suspend fun getAllSpending(): List<SpendingEntity>

    @Query(
        """
        select * from spending_table
        where spendingType = :spendingType
    """
    )
    suspend fun getSpendingBySpendingType(spendingType: String): List<SpendingEntity>
}