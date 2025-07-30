package com.example.apteczka.drugs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apteczka.drugs.data.repository.DrugRepository

class DrugViewModelFactory(
    private val repository: DrugRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrugViewModel::class.java)) {
            return DrugViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
