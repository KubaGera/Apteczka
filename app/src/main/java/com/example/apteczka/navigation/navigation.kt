package com.example.apteczka.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apteczka.auth.viewmodel.AuthViewModel
import com.example.apteczka.drugs.ui.AddEditDrugScreen
import com.example.apteczka.drugs.ui.DrugListScreen
import com.example.apteczka.drugs.viewmodel.DrugViewModel
import com.example.apteczka.history.HistoryScreen
import com.example.apteczka.home.HomeScreen
import com.example.apteczka.visit.ui.AddVisitScreen
import com.example.apteczka.visit.ui.VisitListScreen
import com.example.apteczka.visit.viewmodel.VisitViewModel

enum class Screens {
    Login, Home, Drugs, AddDrug, Visits, AddVisit, History
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    authViewModel: AuthViewModel,
    drugViewModel: DrugViewModel,
    visitViewModel: VisitViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)  // Zakładam, że masz ten composable gdzieś w projekcie
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screens.Home.name) {
                HomeScreen(
                    drugViewModel = drugViewModel,
                    visitViewModel = visitViewModel,
                )
            }
            composable(Screens.Drugs.name) {
                DrugListScreen(
                drugViewModel = drugViewModel,
                onAddClick = { navController.navigate(Screens.AddDrug.name) }
                )


            }
            composable(Screens.AddDrug.name) {
                AddEditDrugScreen(drugViewModel) {
                    navController.popBackStack()
                }
            }
            composable(Screens.Visits.name) {
                VisitListScreen(
                    visitViewModel = visitViewModel,
                    onAddVisitClick = {
                        navController.navigate(Screens.AddVisit.name)
                    }
                )
            }
            composable(Screens.AddVisit.name) {
                AddVisitScreen(
                    visitViewModel = visitViewModel,
                    onVisitAdded = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Screens.History.name) {
                HistoryScreen()
            }
        }
    }
}
