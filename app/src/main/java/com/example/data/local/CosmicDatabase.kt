package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Database(entities = [CosmicBookmark::class], version = 1, exportSchema = false)
abstract class CosmicDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): CosmicBookmarkDao

    companion object {
        @Volatile
        private var INSTANCE: CosmicDatabase? = null

        fun getDatabase(context: Context): CosmicDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CosmicDatabase::class.java,
                    "cosmic_explorer_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class CosmicRepository(private val dao: CosmicBookmarkDao) {
    val allBookmarks: Flow<List<CosmicBookmark>> = dao.getAllBookmarks()

    fun isBookmarked(targetId: String): Flow<Boolean> = dao.isTargetBookmarked(targetId)

    suspend fun toggleBookmark(
        targetId: String,
        nameEn: String,
        nameBn: String,
        category: String,
        isCurrentlyBookmarked: Boolean,
        userNote: String = ""
    ) {
        if (isCurrentlyBookmarked) {
            dao.deleteByTargetId(targetId)
        } else {
            dao.insertBookmark(
                CosmicBookmark(
                    targetId = targetId,
                    nameEn = nameEn,
                    nameBn = nameBn,
                    category = category,
                    userNote = userNote
                )
            )
        }
    }

    suspend fun saveNote(bookmark: CosmicBookmark, updatedNote: String) {
        dao.insertBookmark(bookmark.copy(userNote = updatedNote))
    }

    suspend fun deleteBookmark(bookmark: CosmicBookmark) {
        dao.deleteBookmark(bookmark)
    }
}
