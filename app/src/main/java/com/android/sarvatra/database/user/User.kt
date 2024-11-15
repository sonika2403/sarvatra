package com.android.sarvatra.database.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val username: String,
    val name: String,
    val password: String,
    val photoUri: String? = null,
    val email: String,
    val loginTime: String = System.currentTimeMillis().toString(),
    val gender: String,
    val mobile: String,
    val dateOfBirth: String,
    val role: String = "admin",
    val tier: String = "free"
)
