package com.example.apteczka.auth.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp

@Composable
fun RoleSelection(onPatient: () -> Unit, onCaregiver: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Wybierz rolę:")

        Button(onClick = onPatient, modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Pacjent")
        }

        Button(onClick = onCaregiver, modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Opiekun")
        }
    }
}
