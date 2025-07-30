package com.example.apteczka.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apteczka.auth.viewmodel.AuthViewModel

@Composable
fun LoginScreen(viewModel: AuthViewModel, onLoginSuccess: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Login") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Hasło") })

        Button(
            onClick = {
                viewModel.login(username, password, asCaregiver = false)
                onLoginSuccess()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Zaloguj się")
        }
    }
}
