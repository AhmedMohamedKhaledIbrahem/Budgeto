package com.budgeto.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spending_table")
data class SpendingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val description: String,
    val amountCents: Long,
    val date: Long,
    val category: String,
    val spendingType: String,
)
