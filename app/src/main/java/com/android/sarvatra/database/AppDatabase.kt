package com.android.sarvatra.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.sarvatra.database.user.User
import com.android.sarvatra.database.user.UserDao
import com.android.sarvatra.database.todo.Todo
import com.android.sarvatra.database.todo.TodoDao
import com.android.sarvatra.database.journal.Journal
import com.android.sarvatra.database.journal.JournalDao

@Database(entities = [User::class, Todo::class, Journal::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun todoDao(): TodoDao
    abstract fun journalDao(): JournalDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sarvatra_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
