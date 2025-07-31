package com.example.apteczka.drugs.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apteczka.drugs.viewmodel.DrugViewModel

@Composable
fun DrugListScreen(
    drugViewModel: DrugViewModel,
    onAddClick: () -> Unit
) {
    val drugs by drugViewModel.drugs.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+") // lub ikona
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            items(drugs) { drug ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Nazwa: ${drug.name}")
                        Text(text = "Dawka dzienna: ${drug.dailyDose}")
                        Text(text = "Pozostało: ${drug.remainingQuantity}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(onClick = {
                                val updated = drug.copy(dailyDose = drug.dailyDose + 1)
                                drugViewModel.updateDrug(updated)
                            }) {
                                Text("Zwiększ dawkę")
                            }

                            Button(
                                onClick = { drugViewModel.deleteDrug(drug) },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Usuń")
                            }
                        }
                    }
                }
            }
        }
    }
}
