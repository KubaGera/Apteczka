@file:Suppress("AndroidUnresolvedRoomSqlReference")

package com.example.apteczka.auth.data.dao

import androidx.room.*
import com.example.apteczka.user.User

import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)
}