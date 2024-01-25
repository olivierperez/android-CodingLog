package fr.o80.codinglog.domain

import android.content.Context
import fr.o80.codinglog.data.CodingDatabase
import fr.o80.codinglog.data.entity.LogInfoEntity
import fr.o80.codinglog.data.openCodingDatabase
import java.util.Date

class LogNewInfoUseCase(
    context: Context
) {
    private val db: CodingDatabase = openCodingDatabase(context)

    suspend operator fun invoke(
        category: String,
        title: String,
        parameters: Map<String, String>?
    ) {
        db.logInfoDao().insert(LogInfoEntity(0, Date(), category, title, parameters))
    }
}
