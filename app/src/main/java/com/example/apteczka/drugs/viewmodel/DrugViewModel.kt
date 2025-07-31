package com.example.apteczka.drugs.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apteczka.drugs.data.repository.DrugRepository
import com.example.apteczka.drugs.model.Drug
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class DrugViewModel(private val repository: DrugRepository) : ViewModel() {

    private val _drugs = MutableStateFlow<List<Drug>>(emptyList())
    val drugs: StateFlow<List<Drug>> = _drugs

    init {
        refreshDrugs()
    }

    private fun refreshDrugs() {
        viewModelScope.launch {
            val allDrugs = repository.getAllDrugs()
            _drugs.value = allDrugs
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun takeDose(drug: Drug) {
        viewModelScope.launch {
            if (drug.remainingQuantity >= drug.dailyDose && drug.dailyDose > 0) {
                val newRemaining = drug.remainingQuantity - drug.dailyDose
                val newNextDoseDate = drug.nextDoseDate?.plusDays(1) ?: LocalDate.now().plusDays(1)

                val updatedDrug = drug.copy(
                    remainingQuantity = newRemaining,
                    nextDoseDate = newNextDoseDate
                )

                repository.updateDrug(updatedDrug)
                refreshDrugs()  // odśwież listę po aktualizacji
            }
        }
    }

    // Filtruj leki, które mają remainingQuantity > 0 i nextDoseDate nie po dzisiejszym dniu
    @RequiresApi(Build.VERSION_CODES.O)
    fun drugsToTakeToday(): List<Drug> {
        val today = LocalDate.now()
        return _drugs.value.filter { drug ->
            drug.remainingQuantity > 0 && (drug.nextDoseDate == null || !drug.nextDoseDate.isAfter(today))
        }
    }

    fun addDrug(drug: Drug) {
        viewModelScope.launch {
            repository.insertDrug(drug)
            refreshDrugs()
        }
    }
    fun deleteDrug(drug: Drug) {
        viewModelScope.launch {
            repository.deleteDrug(drug)
            refreshDrugs()
        }
    }

    fun updateDrug(drug: Drug) {
        viewModelScope.launch {
            repository.updateDrug(drug)
            refreshDrugs()
        }
    }
}
