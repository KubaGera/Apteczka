package com.example.apteczka.drugs.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apteczka.drugs.model.Drug
import com.example.apteczka.drugs.data.repository.DrugRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class DrugViewModel(private val repository: DrugRepository) : ViewModel() {

    private val _drugs = MutableStateFlow<List<Drug>>(emptyList())
    val drugs: StateFlow<List<Drug>> = _drugs

    init {
        loadDrugs()
    }

    private fun loadDrugs() {
        viewModelScope.launch {
            repository.getAllDrugs().collect { list ->
                _drugs.value = list
            }
        }
    }

    fun addDrug(drug: Drug) {
        viewModelScope.launch {
            repository.insertDrug(drug)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun markDrugAsTaken(drug: Drug) {
        val updatedDrug = drug.copy(
            remainingQuantity = (drug.remainingQuantity - drug.dailyDose).coerceAtLeast(0),
            nextDoseDate = drug.nextDoseDate?.plusDays(1)
        )
        viewModelScope.launch {
            repository.updateDrug(updatedDrug)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDrugsToTakeToday(): List<Drug> {
        val today = LocalDate.now()
        return _drugs.value.filter { it.nextDoseDate == today }
    }

    fun getDrugsRunningLow(threshold: Int = 5): List<Drug> {
        return _drugs.value.filter { it.remainingQuantity <= threshold }
    }
}
