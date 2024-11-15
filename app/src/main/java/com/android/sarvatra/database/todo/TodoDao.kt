package com.android.sarvatra.database.todo

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.sql.Timestamp

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo WHERE username = :username ORDER BY timeCreated DESC")
    fun getAllTodosByUser(username: String): Flow<List<Todo>>

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getTodoById(id: Int): Todo?

    @Query("SELECT * FROM todo WHERE username = :username AND status = 'Pending' ORDER BY timeToDo ASC")
    fun getPendingTodosByUser(username: String): Flow<List<Todo>>

    @Query("SELECT * FROM todo WHERE username = :username AND status = 'Completed' ORDER BY timeToDo ASC")
    fun getCompletedTodosByUser(username: String): Flow<List<Todo>>

    @Query("SELECT * FROM todo WHERE username = :username ORDER BY CASE taskPriority WHEN 'High' THEN 1 WHEN 'Normal' THEN 2 ELSE 3 END")
    fun getTodosByUserOrderedByPriority(username: String): Flow<List<Todo>>

    @Query("UPDATE todo SET timeToDo = :newTime WHERE id = :id")
    suspend fun updateTimeToDo(id: Int, newTime: String)
}
