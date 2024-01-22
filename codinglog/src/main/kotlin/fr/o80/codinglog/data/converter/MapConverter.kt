package fr.o80.codinglog.data.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class MapConverter {
    private val json = Json

    @TypeConverter
    fun fromString(value: String?): Map<String, String>? {
        return value?.let { json.decodeFromString(it) }
    }

    @TypeConverter
    fun dateToTimestamp(map: Map<String, String>?): String? {
        return map?.let { json.encodeToString(it) }
    }
}