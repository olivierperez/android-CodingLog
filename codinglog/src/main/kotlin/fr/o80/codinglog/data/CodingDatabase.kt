package fr.o80.codinglog.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.o80.codinglog.data.converter.DateConverter
import fr.o80.codinglog.data.converter.MapConverter
import fr.o80.codinglog.data.entity.LogInfoEntity

@Database(
    entities = [LogInfoEntity::class],
    version = 1
)
@TypeConverters(MapConverter::class, DateConverter::class)
internal abstract class CodingDatabase : RoomDatabase() {
    abstract fun logInfoDao(): LogInfoDao
}

internal fun openCodingDatabase(context: Context) = Room.databaseBuilder(
    context,
    CodingDatabase::class.java,
    "coding-database"
).build()