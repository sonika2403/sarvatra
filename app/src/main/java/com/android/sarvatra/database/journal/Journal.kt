package com.android.sarvatra.database.journal

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "journal")
data class Journal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val email: String,
    val isFav: Boolean = false,
    val timeCreated: String = System.currentTimeMillis().toString(),
    val title: String,
    val body: String
)
