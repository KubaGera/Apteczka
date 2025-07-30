package com.example.apteczka.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apteczka.auth.data.model.User
import com.example.apteczka.auth.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _isCaregiver = MutableStateFlow(false)
    val isCaregiver = _isCaregiver.asStateFlow()

    fun login(username: String, password: String, asCaregiver: Boolean) {
        viewModelScope.launch {
            val user = repository.login(username, password)
            _isLoggedIn.value = user != null
            _isCaregiver.value = user?.isCaregiver ?: false
        }
    }

    fun register(username: String, password: String, isCaregiver: Boolean) {
        viewModelScope.launch {
            repository.register(User(username, password, isCaregiver))
            _isLoggedIn.value = true
            _isCaregiver.value = isCaregiver
        }
    }

    fun logout() {
        _isLoggedIn.value = false
        _isCaregiver.value = false
    }
}
