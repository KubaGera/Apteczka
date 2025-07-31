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
    visitViewModel: VisitViewModel
) {
    val allDrugs by drugViewModel.drugs.collectAsState(initial = emptyList())
    val nearestVisit by visitViewModel.nearestVisit.collectAsState()
    val takenDrugs = remember { mutableStateMapOf<Int, Boolean>() }

    val drugsToday = remember(allDrugs) {
        allDrugs.filter { it.dailyDose > 0 && it.remainingQuantity > 0 &&
                (it.nextDoseDate == null || !it.nextDoseDate.isAfter(LocalDate.now()))
        }
    }

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
                                if (checked) drugViewModel.takeDose(drug)
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
            Text("Typ wizyty: ${nearestVisit?.title}")
            Text("Data: ${nearestVisit?.date}")
        }
    }
}
