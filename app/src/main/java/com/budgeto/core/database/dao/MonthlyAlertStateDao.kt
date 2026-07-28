package com.budgeto.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.budgeto.core.database.entity.MonthlyAlertStateEntity

@Dao
interface MonthlyAlertStateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAlertState(alertState: MonthlyAlertStateEntity)

    @Query("SELECT * FROM monthly_alert_state_table WHERE monthStart = :monthStart LIMIT 1")
    suspend fun getAlertState(monthStart: Long): MonthlyAlertStateEntity?
}
