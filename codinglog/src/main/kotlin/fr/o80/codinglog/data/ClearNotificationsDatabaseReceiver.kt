package fr.o80.codinglog.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import fr.o80.codinglog.notifier.NotificationBuffer
import kotlinx.coroutines.runBlocking

internal class ClearNotificationsDatabaseReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra("NOTIFICATION_ID", 0)
        val category = intent.getStringExtra("CATEGORY") ?: error("Missing category")

        clearDatabase(context, category)
        dismissNotifications(context, notificationId, category)
    }

    private fun clearDatabase(context: Context, category: String) {
        val db = openCodingDatabase(context)

        runBlocking {
            db.logInfoDao().deleteCategory(category)
        }
    }

    private fun dismissNotifications(context: Context, notificationId: Int, category: String) {
        NotificationManagerCompat.from(context)
            .cancel(notificationId)
        NotificationBuffer.clearCategory(category)
    }
}