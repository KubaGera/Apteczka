package com.example.apteczka.visit.data.repository

import com.example.apteczka.visit.data.dao.VisitDao
import com.example.apteczka.visit.model.Visit


class VisitRepository(private val visitDao: VisitDao) {

    suspend fun getAllVisits(): List<Visit> = visitDao.getAll()

    suspend fun insertVisit(visit: Visit): Long = visitDao.insert(visit)
}
