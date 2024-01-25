package fr.o80.codinglog.domain.model

import java.util.Date

data class LogInfo(
    val id: Int,
    val createdAt: Date,
    val category: String,
    val title: String,
    val parameters: Map<String, String>?
)
