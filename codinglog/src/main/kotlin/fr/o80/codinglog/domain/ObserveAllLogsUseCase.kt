package fr.o80.codinglog.domain

import android.content.Context
import fr.o80.codinglog.data.CodingDatabase
import fr.o80.codinglog.data.entity.LogInfoEntity
import fr.o80.codinglog.data.openCodingDatabase
import fr.o80.codinglog.domain.model.LogInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveAllLogsUseCase(
    val context: Context
) {
    private val db: CodingDatabase = openCodingDatabase(context)

    operator fun invoke(): Flow<LogInfoResult> {
        return db.logInfoDao().getAll()
            .map { logs ->
                LogInfoResult(
                    logs = logs.map { it.toLogInfo() }.sortedByDescending { it.createdAt },
                    categories = logs.distinctCategories()
                )
            }
    }
}

private fun List<LogInfoEntity>.distinctCategories(): List<String> =
    this.map { log -> log.category }
        .distinct()
        .sorted()

private fun LogInfoEntity.toLogInfo(): LogInfo = LogInfo(
    id = uid,
    createdAt = createdAt,
    category = category,
    title = title,
    parameters = parameters
)

data class LogInfoResult(
    val logs: List<LogInfo>,
    val categories: List<String>
)