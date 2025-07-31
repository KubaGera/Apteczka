package com.example.apteczka.visit.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apteczka.visit.data.repository.VisitRepository
import com.example.apteczka.visit.model.Visit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class VisitViewModel(private val repository: VisitRepository) : ViewModel() {

    private val _visits = MutableStateFlow<List<Visit>>(emptyList())
    val visits: StateFlow<List<Visit>> = _visits
    val nearestVisit: StateFlow<Visit?> = _visits
        .map { visitsList ->
            val today = LocalDate.now()
            visitsList
                .filter { !it.date.isBefore(today) } // daty dzisiejsze i przyszłe
                .minByOrNull { it.date }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    init {
        refreshVisits()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshVisits() {
        viewModelScope.launch {
            val allVisits = repository.getAllVisits()
            _visits.value = allVisits
            removePastVisits()  // usuń przeterminowane wizyty po załadowaniu
        }
    }

    fun addVisit(visit: Visit, onVisitAdded: (visitId: Long) -> Unit) {
        viewModelScope.launch {
            val visitId = repository.insertVisit(visit)
            onVisitAdded(visitId)
            refreshVisits()
        }
    }

    fun deleteVisit(visit: Visit) {
        viewModelScope.launch {
            repository.deleteVisit(visit)
            refreshVisits()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun removePastVisits() {
        val today = LocalDate.now()
        // Filtrujemy przeterminowane wizyty i usuwamy je z repozytorium
        val expiredVisits = _visits.value.filter { it.date.isBefore(today) }
        for (visit in expiredVisits) {
            repository.deleteVisit(visit)
        }
        // Po usunięciu odświeżamy listę wizyt
        _visits.value = repository.getAllVisits()
    }
}
