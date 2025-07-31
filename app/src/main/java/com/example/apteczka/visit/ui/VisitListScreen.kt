package com.example.apteczka.visit.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apteczka.visit.viewmodel.VisitViewModel
import com.example.apteczka.visit.model.Visit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VisitListScreen(
    visitViewModel: VisitViewModel,
    onAddVisitClick: () -> Unit
) {
    val visits = visitViewModel.visits.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Twoje wizyty", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAddVisitClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Dodaj wizytę")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (visits.value.isEmpty()) {
            Text("Brak zaplanowanych wizyt.")
        } else {
            LazyColumn {
                items(visits.value.sortedBy { it.date }) { visit ->
                    VisitItem(visit = visit)
                }
            }
        }
    }

}
