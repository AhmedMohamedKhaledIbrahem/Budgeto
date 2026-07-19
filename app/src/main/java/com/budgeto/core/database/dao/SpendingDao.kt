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

    @Query("""
        select coalesce(sum(amountCents),0)
        from spending_table where date like :month || '%'
    """)
    suspend fun getSpendingByMonth(month: String) : Long
}