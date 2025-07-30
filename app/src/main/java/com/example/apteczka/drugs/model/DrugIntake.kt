package com.example.apteczka.drugs.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class DrugIntake(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val drugId: Int,
    val date: LocalDate
)
