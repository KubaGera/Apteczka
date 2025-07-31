package com.example.apteczka.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apteczka.auth.data.model.User
import com.example.apteczka.auth.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _isCaregiver = MutableStateFlow(false)
    val isCaregiver: StateFlow<Boolean> = _isCaregiver

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.login(username, password)
            if (user != null) {
                _isLoggedIn.value = true
                _isCaregiver.value = user.isCaregiver
                _errorMessage.value = null
            } else {
                _errorMessage.value = "Niepoprawne dane logowania"
            }
        }
    }

    fun register(username: String, password: String, isCaregiver: Boolean) {
        viewModelScope.launch {
            try {
                repository.register(User(username = username, password = password, isCaregiver = isCaregiver))
                _isLoggedIn.value = true
                _isCaregiver.value = isCaregiver
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Użytkownik o tej nazwie już istnieje"
            }
        }
    }

    fun logout() {
        _isLoggedIn.value = false
        _isCaregiver.value = false
        _errorMessage.value = null
    }
}
