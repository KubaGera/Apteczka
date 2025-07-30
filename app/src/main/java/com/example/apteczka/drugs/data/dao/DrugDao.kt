package com.example.apteczka.drugs.data.dao

import androidx.room.*
import com.example.apteczka.drugs.model.Drug
import kotlinx.coroutines.flow.Flow

@Dao
interface DrugDao {
    @Query("SELECT * FROM Drug")
    fun getAllDrugs(): Flow<List<Drug>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrug(drug: Drug)

    @Delete
    suspend fun deleteDrug(drug: Drug)

    @Update
    suspend fun updateDrug(drug: Drug)
}