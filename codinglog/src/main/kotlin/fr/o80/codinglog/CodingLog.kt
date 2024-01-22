package fr.o80.codinglog

import android.content.Context
import fr.o80.codinglog.data.entity.LogInfoEntity
import fr.o80.codinglog.data.openCodingDatabase
import fr.o80.codinglog.notifier.Notifier
import java.util.Date

class CodingLog(
    context: Context
) {
    private val db = openCodingDatabase(context)

    private val notifier = Notifier(context)

    suspend fun report(
        category: String,
        title: String,
        parameters: Map<String, String>? = null
    ) {
        notifier.notify(category, title, parameters)
        db.logInfoDao().insert(LogInfoEntity(0, Date(), category, title, parameters))
    }

    companion object {
        var NOTIFICATION_GROUP_ID: String = "CodingLog"
        var NOTIFICATION_GROUP_NAME: String = "Coding Log"
    }
}