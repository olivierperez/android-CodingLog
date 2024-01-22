package fr.o80.codinglog.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.o80.codinglog.data.entity.LogInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface LogInfoDao {
    @Query("SELECT * FROM LogInfoEntity")
    fun getAll(): Flow<List<LogInfoEntity>>

    @Insert
    suspend fun insert(logInfo: LogInfoEntity)

    @Query("DELETE FROM LogInfoEntity")
    suspend fun deleteAll()

    @Query("DELETE FROM LogInfoEntity WHERE category = :category")
    suspend fun deleteCategory(category: String)
}