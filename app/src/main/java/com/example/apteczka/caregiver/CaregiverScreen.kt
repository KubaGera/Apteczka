package com.example.apteczka.caregiver

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CaregiverScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Panel Opiekuna", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Text("Tutaj opiekun może zarządzać użytkownikami lub monitorować ich dane.")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onBack) {
            Text("Powrót")
        }
    }
}
