package com.android.sarvatra.database.user

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user ORDER BY loginTime ASC LIMIT 1")
    suspend fun getFirstUser(): User?

    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM user WHERE username = :username")
    fun getUserFlowByUsername(username: String): Flow<User>

    @Query("SELECT * FROM user WHERE role = :role")
    fun getUsersByRole(role: String): Flow<List<User>>

    @Query("SELECT * FROM user ORDER BY loginTime DESC")
    fun getAllUsersOrderedByLoginTime(): Flow<List<User>>
}
