package com.example.apteczka.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apteczka.auth.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(viewModel: AuthViewModel, onRegisterSuccess: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isCaregiver by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Login") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Hasło") })
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(checked = isCaregiver, onCheckedChange = { isCaregiver = it })
            Text("Jestem opiekunem")
        }

        Button(
            onClick = {
                viewModel.register(username, password, isCaregiver)
                onRegisterSuccess()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Zarejestruj się")
        }
    }
}
