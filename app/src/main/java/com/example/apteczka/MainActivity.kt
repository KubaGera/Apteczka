package com.example.apteczka

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
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
import com.example.apteczka.auth.ui.LoginScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🛎️ Tworzenie kanału powiadomień (dla przypomnień o wizytach)
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

                // ViewModel: Auth
                val authRepository = AuthRepository(db.userDao())
                val authFactory = AuthViewModelFactory(authRepository)
                val authViewModel: AuthViewModel = viewModel(factory = authFactory)

                // ViewModel: Leki
                val drugRepository = DrugRepository(db.drugDao())
                val drugFactory = DrugViewModelFactory(drugRepository)
                val drugViewModel: DrugViewModel = viewModel(factory = drugFactory)

                // ViewModel: Wizyty
                val visitRepository = VisitRepository(db.visitDao())
                val visitFactory = VisitViewModelFactory(visitRepository)
                val visitViewModel: VisitViewModel = viewModel(factory = visitFactory)

                // Obserwuj, czy użytkownik jest zalogowany
                val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

                if (!isLoggedIn) {
                    // 🔐 Ekran logowania (jeśli nie zalogowano)
                    LoginScreen(authViewModel = authViewModel)
                } else {
                    // 🧭 Nawigacja główna (jeśli zalogowano)
                    AppNavigation(
                        authViewModel = authViewModel,
                        drugViewModel = drugViewModel,
                        visitViewModel = visitViewModel
                    )
                }
            }
        }
    }
}
