package com.example.apteczka.drugs.data.repository

import com.example.apteczka.drugs.data.dao.DrugDao
import com.example.apteczka.drugs.model.Drug
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DrugRepository(private val drugDao: DrugDao) {

    suspend fun getAllDrugs(): List<Drug> {
        return drugDao.getAllDrugs()
    }

    suspend fun insertDrug(drug: Drug) {
        drugDao.insertDrug(drug)
    }

    suspend fun updateDrug(drug: Drug) {
        drugDao.updateDrug(drug)
    }

    suspend fun deleteDrug(drug: Drug) {
        drugDao.deleteDrug(drug)
    }

}
