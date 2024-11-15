package com.android.sarvatra.database.todo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val email: String,
    val timeCreated: String = System.currentTimeMillis().toString(),
    val timeToDo: String,
    val taskName: String,
    val taskDetail: String,
    val status: String = "Pending",
    val taskPriority: String = "Normal"
)
