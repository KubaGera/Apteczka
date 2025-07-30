package com.example.apteczka

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apteczka.auth.data.repository.AuthRepository
import com.example.apteczka.auth.viewmodel.AuthViewModel
import com.example.apteczka.auth.viewmodel.AuthViewModelFactory
import com.example.apteczka.database.AppDatabase
import com.example.apteczka.drugs.data.repository.DrugRepository
import com.example.apteczka.drugs.viewmodel.DrugViewModel
import com.example.apteczka.drugs.viewmodel.DrugViewModelFactory
import com.example.apteczka.navigation.AppNavigation
import com.example.apteczka.ui.theme.ApteczkaTheme
import com.example.apteczka.visit.data.repository.VisitRepository
import com.example.apteczka.visit.viewmodel.VisitViewModel
import com.example.apteczka.visit.viewmodel.VisitViewModelFactory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 👉 Utwórz kanał powiadomień dla przypomnień
        val channel = NotificationChannel(
            "visit_channel",
            "Przypomnienia o wizytach",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Powiadomienia o zbliżających się wizytach lekarskich"
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        setContent {
            ApteczkaTheme {
                val context = this
                val db = AppDatabase.getInstance(context)

                val authRepository = AuthRepository(db.userDao())
                val authFactory = AuthViewModelFactory(authRepository)
                val authViewModel: AuthViewModel = viewModel(factory = authFactory)

                val drugRepository = DrugRepository(db.drugDao())
                val drugFactory = DrugViewModelFactory(drugRepository)
                val drugViewModel: DrugViewModel = viewModel(factory = drugFactory)

                val visitRepository = VisitRepository(db.visitDao())
                val visitViewModelFactory = VisitViewModelFactory(visitRepository)
                val visitViewModel: VisitViewModel = viewModel(factory = visitViewModelFactory)

                AppNavigation(
                    authViewModel = authViewModel,
                    drugViewModel = drugViewModel,
                    visitViewModel = visitViewModel
                )
            }
        }
    }
}
