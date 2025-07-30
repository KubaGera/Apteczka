package com.example.apteczka.visit.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.example.apteczka.visit.data.repository.VisitRepository
import com.example.apteczka.visit.model.Visit
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class VisitViewModel(private val repository: VisitRepository) : ViewModel() {

    private val _visits = MutableStateFlow<List<Visit>>(emptyList())
    val visits: StateFlow<List<Visit>> = _visits

    // Nowy StateFlow dla najbliższej wizyty
    private val _nearestVisit = MutableStateFlow<Visit?>(null)
    val nearestVisit: StateFlow<Visit?> = _nearestVisit

    init {
        loadVisits()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadVisits() {
        viewModelScope.launch {
            val allVisits = repository.getAllVisits()
            _visits.value = allVisits
            updateNearestVisit(allVisits)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateNearestVisit(visits: List<Visit>) {
        val today = LocalDate.now()
        val nearest = visits
            .filter { it.date >= today }
            .minByOrNull { it.date }
        _nearestVisit.value = nearest
    }

    fun addVisit(visit: Visit, onVisitSaved: (visitId: Int) -> Unit) {
        viewModelScope.launch {
            val id = repository.insertVisit(visit).toInt()
            loadVisits()
            onVisitSaved(id)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUpcomingVisits(): List<Visit> {
        val today = LocalDate.now()
        return _visits.value.filter { it.date >= today }
    }
}

