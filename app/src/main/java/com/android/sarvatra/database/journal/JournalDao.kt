package com.android.sarvatra.database.journal

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journal: Journal)

    @Delete
    suspend fun deleteJournal(journal: Journal)

    @Query("DELETE FROM journal WHERE id = :id")
    suspend fun deleteJournalById(id: Int)

    @Query("SELECT * FROM journal WHERE username = :username ORDER BY timeCreated DESC")
    fun getAllJournalsByUser(username: String): Flow<List<Journal>>

    @Query("SELECT * FROM journal WHERE id = :id")
    suspend fun getJournalById(id: Int): Journal?

    @Query("SELECT * FROM journal WHERE username = :username AND isFav = 1 ORDER BY timeCreated DESC")
    fun getFavoriteJournalsByUser(username: String): Flow<List<Journal>>

    @Query("UPDATE journal SET isFav = :isFav WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFav: Boolean)

    @Query("SELECT * FROM journal ORDER BY timeCreated DESC") // Fetch journals sorted by creation time
    suspend fun getAllJournals(): List<Journal>

}
