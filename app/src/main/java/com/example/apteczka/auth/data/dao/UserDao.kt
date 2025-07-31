package com.example.apteczka.auth.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.apteczka.auth.data.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getUser(username: String, password: String): User?
}