package com.example.apteczka.drugs.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.apteczka.drugs.model.Drug
import com.example.apteczka.drugs.ui.component.DrugItem
import com.example.apteczka.drugs.viewmodel.DrugViewModel

@Composable
fun DrugListScreen(viewModel: DrugViewModel, onAddDrug: () -> Unit) {
    val drugs = viewModel.drugs.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddDrug) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            items(drugs.value) { drug ->
                DrugItem(drug = drug, onClick = { /* obsługa kliknięcia */ })
            }
        }
    }
}

