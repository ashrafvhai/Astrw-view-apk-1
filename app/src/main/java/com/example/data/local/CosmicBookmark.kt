package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "cosmic_bookmarks")
data class CosmicBookmark(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val targetId: String,
    val nameEn: String,
    val nameBn: String,
    val category: String,
    val userNote: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface CosmicBookmarkDao {
    @Query("SELECT * FROM cosmic_bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<CosmicBookmark>>

    @Query("SELECT EXISTS(SELECT 1 FROM cosmic_bookmarks WHERE targetId = :targetId)")
    fun isTargetBookmarked(targetId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: CosmicBookmark): Long

    @Query("DELETE FROM cosmic_bookmarks WHERE targetId = :targetId")
    suspend fun deleteByTargetId(targetId: String)

    @Delete
    suspend fun deleteBookmark(bookmark: CosmicBookmark)
}
