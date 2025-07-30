package com.example.apteczka.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.apteczka.auth.data.dao.UserDao
import com.example.apteczka.auth.data.model.User
import com.example.apteczka.drugs.data.dao.DrugDao
import com.example.apteczka.drugs.model.Drug
import com.example.apteczka.drugs.model.DrugIntake
import com.example.apteczka.visit.data.dao.VisitDao
import com.example.apteczka.visit.model.Visit

 // Dodaj jeśli masz osobny DAO

@Database(
    entities = [
        User::class,
        Drug::class,
        DrugIntake::class,
        Visit::class
    ],
    version = 2

)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun drugDao(): DrugDao
    abstract fun visitDao(): VisitDao // jeśli masz, dodaj

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "apteczka.db"
                ).fallbackToDestructiveMigration()
                .build().also { INSTANCE = it }
            }
        }
    }
}
