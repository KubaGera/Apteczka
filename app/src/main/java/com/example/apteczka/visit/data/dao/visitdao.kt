package com.example.apteczka.visit.data.dao

import com.example.apteczka.visit.model.Visit
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VisitDao {

    @Query("SELECT * FROM visits ORDER BY date ASC")
    suspend fun getAll(): List<Visit>

    @Insert
    suspend fun insert(visit: Visit): Long

    @Delete
    suspend fun delete(visit: Visit)
}