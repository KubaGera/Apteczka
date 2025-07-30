package com.example.apteczka.visit.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.apteczka.reminders.ReminderScheduler
import com.example.apteczka.visit.model.Visit
import com.example.apteczka.visit.viewmodel.VisitViewModel
import java.time.LocalDate
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import java.util.Calendar


@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddVisitScreen(
    visitViewModel: VisitViewModel,
    onVisitAdded: () -> Unit
) {
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(LocalDate.now()) }  // Zmienna data

    // Ustawienie kalendarza do DatePickerDialog
    val calendar = Calendar.getInstance()
    calendar.set(date.year, date.monthValue - 1, date.dayOfMonth)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date = LocalDate.of(year, month + 1, dayOfMonth)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Tytuł") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Notatka") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Klikalny tekst pokazujący wybraną datę
        Text(
            text = "Data wizyty: $date",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
                .padding(vertical = 8.dp)
        )

        Button(
            onClick = {
                val visit = Visit(title = title, note = note, date = date)
                visitViewModel.addVisit(visit) { visitId ->
                    ReminderScheduler.scheduleVisitReminders(
                        context = context,
                        visitId = visitId,
                        title = title,
                        date = date
                    )
                    onVisitAdded()
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Dodaj wizytę")
        }
    }
}
