package fr.o80.codinglog

import android.content.Context
import android.content.Intent
import fr.o80.codinglog.domain.LogNewInfoUseCase
import fr.o80.codinglog.notifier.Notifier
import fr.o80.codinglog.ui.CodingLogActivity

class CodingLog(
    context: Context,
    private val logNewInfo: LogNewInfoUseCase = LogNewInfoUseCase(context)
) {

    private val notifier = Notifier(context)

    suspend fun report(
        category: String,
        title: String,
        parameters: Map<String, String>? = null
    ) {
        notifier.notify(category, title, parameters)
        logNewInfo(category, title, parameters)
    }

    companion object {
        var NOTIFICATION_GROUP_ID: String = "CodingLog"
        var NOTIFICATION_GROUP_NAME: String = "Coding Log"

        fun intent(context: Context): Intent =
            Intent(context, CodingLogActivity::class.java).apply {
                flags= Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
    }
}