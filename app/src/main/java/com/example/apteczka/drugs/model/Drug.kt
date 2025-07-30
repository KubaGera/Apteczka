package com.example.apteczka.drugs.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Drug(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val totalAmount: Int,        // ile leków jest na starcie
    val dailyDose: Int,          // ile bierzesz dziennie
    val remainingQuantity: Int,  // ile zostało
    val nextDoseDate: LocalDate?,
    val expirationDate: LocalDate?
)