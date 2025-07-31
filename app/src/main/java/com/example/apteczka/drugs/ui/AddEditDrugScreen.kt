package com.example.apteczka.drugs.ui



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apteczka.drugs.model.Drug
import com.example.apteczka.drugs.viewmodel.DrugViewModel

@Composable
fun AddEditDrugScreen(viewModel: DrugViewModel, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var total by remember { mutableStateOf("") }
    var daily by remember { mutableStateOf("") }

    // Możesz też dodać pola do edycji remainingQuantity, nextDoseDate, expirationDate jeśli chcesz

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Dodaj lek", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nazwa") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = total,
            onValueChange = { total = it.filter { char -> char.isDigit() } }, // tylko cyfry
            label = { Text("Ilość") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = daily,
            onValueChange = { daily = it.filter { char -> char.isDigit() } },
            label = { Text("Dawka dzienna") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (name.isNotBlank() && total.isNotBlank() && daily.isNotBlank()) {
                viewModel.addDrug(
                    Drug(
                        name = name,
                        totalAmount = total.toInt(),
                        dailyDose = daily.toInt(),
                        remainingQuantity = total.toInt(),
                        nextDoseDate = null,
                        expirationDate = null
                    )
                )
                onBack()
            } else {
                // Możesz tu dać np. Toast z info o błędzie, jeśli chcesz
            }
        }) {
            Text("Zapisz")
        }
    }
}

