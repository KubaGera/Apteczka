package com.example.apteczka.reminders

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import java.time.LocalDate
import java.time.ZoneId

object ReminderScheduler {

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleVisitReminders(context: Context, visitId: Long, title: String, date: LocalDate) {
        val daysBefore = listOf(7, 3, 1)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        for (day in daysBefore) {
            val triggerDate = date.minusDays(day.toLong())
            val triggerMillis = triggerDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

            if (triggerMillis < System.currentTimeMillis()) continue // nie planuj przeszłości

            val intent = Intent(context, ReminderReceiver::class.java).apply {
                putExtra("title", "Wizyta za $day dni: $title")
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                (visitId + day).toInt(), // unikalne ID dla każdej wizyty + dnia
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerMillis,
                pendingIntent
            )
        }
    }
}
