package fr.o80.codinglog.domain

import fr.o80.codinglog.data.CodingDatabase
import fr.o80.codinglog.data.entity.LogInfoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ObserveAllLogsUseCase(
    private val db: CodingDatabase
) {
    operator fun invoke(): Flow<LogInfoResult> {
        return db.logInfoDao().getAll()
            .map { logs ->
                LogInfoResult(
                    logs = logs.sortedByDescending { it.createdAt },
                    categories = logs.map { log -> log.category }.distinct().sorted()
                )
            }
    }
}

internal data class LogInfoResult(
    val logs: List<LogInfoEntity>,
    val categories: List<String>
)