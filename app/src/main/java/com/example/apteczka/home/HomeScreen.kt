package com.example.apteczka.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apteczka.drugs.model.Drug
import com.example.apteczka.drugs.viewmodel.DrugViewModel
import com.example.apteczka.visit.viewmodel.VisitViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    drugViewModel: DrugViewModel,
    visitViewModel: VisitViewModel,
    onAddDrugClick: () -> Unit,
    onAddVisitClick: () -> Unit
) {
    // Zakładam, że masz w drugViewModel StateFlow<List<Drug>> z lekami
    val allDrugs by drugViewModel.drugs.collectAsState(initial = emptyList())

    // Filtrowanie leków do wzięcia dzisiaj
    val drugsToday = remember(allDrugs) {
        allDrugs.filter { /* warunek na leki do dzisiaj, np. dailyDose > 0 */ it.dailyDose > 0 }
    }

    // Obserwuj najbliższą wizytę z VisitViewModel
    val nearestVisit by visitViewModel.nearestVisit.collectAsState()

    val takenDrugs = remember { mutableStateMapOf<Int, Boolean>() } // mapowanie id leku na czy wzięty

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Leki na dziś", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        if (drugsToday.isEmpty()) {
            Text("Brak leków do wzięcia dzisiaj")
        } else {
            LazyColumn {
                items(drugsToday) { drug ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = takenDrugs[drug.id] ?: false,
                            onCheckedChange = { checked ->
                                takenDrugs[drug.id] = checked
                            }
                        )
                        Text("${drug.name} - dawka: ${drug.dailyDose}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Divider()
        Spacer(modifier = Modifier.height(24.dp))

        Text("Najbliższa wizyta", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        if (nearestVisit == null) {
            Text("Brak zaplanowanych wizyt")
        } else {
            Text("Typ wizyty: ${nearestVisit?.title ?: "brak"}")
            Text("Data: ${nearestVisit?.date ?: "-"}")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onAddDrugClick) {
            Text("Dodaj nowy lek")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onAddVisitClick) {
            Text("Dodaj nową wizytę")
        }
    }
}