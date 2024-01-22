package fr.o80.codinglog.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
internal data class LogInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    @ColumnInfo(name = "createdAt")
    val createdAt: Date,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "parameters")
    val parameters: Map<String, String>?
)
