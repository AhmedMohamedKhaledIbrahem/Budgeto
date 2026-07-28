package com.budgeto.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthly_alert_state_table")
data class MonthlyAlertStateEntity(
    @PrimaryKey
    val monthStart: Long,
    val alertName: String
)
