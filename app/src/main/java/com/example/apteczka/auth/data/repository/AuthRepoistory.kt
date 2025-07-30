package com.example.apteczka.auth.data.repository

import com.example.apteczka.auth.data.dao.UserDao
import com.example.apteczka.auth.data.model.User

class AuthRepository(private val userDao: UserDao) {
    suspend fun register(user: User) {
        userDao.insertUser(user)
    }

    suspend fun login(username: String, password: String): User? {
        return userDao.getUser(username, password)
    }


}
