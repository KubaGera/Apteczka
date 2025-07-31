package com.example.apteczka.drugs.data.dao

import androidx.room.*
import com.example.apteczka.drugs.model.Drug

@Dao
interface DrugDao {
    @Query("SELECT * FROM Drug")
    suspend fun getAllDrugs(): List<Drug>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrug(drug: Drug)

    @Update
    suspend fun updateDrug(drug: Drug)

    @Delete
    suspend fun deleteDrug(drug: Drug)
}
