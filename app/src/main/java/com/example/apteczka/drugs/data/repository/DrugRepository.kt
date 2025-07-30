package com.example.apteczka.drugs.data.repository

import com.example.apteczka.drugs.data.dao.DrugDao
import com.example.apteczka.drugs.model.Drug
import com.example.apteczka.drugs.model.DrugIntake
import kotlinx.coroutines.flow.Flow

class DrugRepository(private val dao: DrugDao) {
    fun getAllDrugs(): Flow<List<Drug>> = dao.getAllDrugs()

    suspend fun insertDrug(drug: Drug) = dao.insertDrug(drug)

    suspend fun deleteDrug(drug: Drug) = dao.deleteDrug(drug)

    suspend fun updateDrug(drug: Drug) = dao.updateDrug(drug) // ✅ DODAJ TO
}
