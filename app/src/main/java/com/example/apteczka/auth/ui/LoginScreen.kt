package com.example.apteczka.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apteczka.auth.viewmodel.AuthViewModel
import androidx.compose.ui.text.input.PasswordVisualTransformation


@Composable
fun LoginScreen(authViewModel: AuthViewModel) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val isCaregiver by authViewModel.isCaregiver.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isCaregiverSelected by remember { mutableStateOf(false) }
    var isRegisterMode by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = if (isRegisterMode) "Rejestracja" else "Logowanie", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Nazwa użytkownika") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Hasło") }, visualTransformation = PasswordVisualTransformation())

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isCaregiverSelected, onCheckedChange = { isCaregiverSelected = it })
            Text(text = "Jestem opiekunem")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (errorMessage != null) {
            Text(text = errorMessage ?: "", color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(onClick = {
            if (isRegisterMode) {
                authViewModel.register(username, password, isCaregiverSelected)
            } else {
                authViewModel.login(username, password)
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = if (isRegisterMode) "Zarejestruj" else "Zaloguj")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            isRegisterMode = !isRegisterMode
            authViewModel.logout()
        }) {
            Text(text = if (isRegisterMode) "Masz konto? Zaloguj się" else "Nie masz konta? Zarejestruj się")
        }

        if (isLoggedIn) {
            Text(text = "Zalogowano pomyślnie! Rola: ${if (isCaregiver) "Opiekun" else "Użytkownik"}")
        }
    }
}