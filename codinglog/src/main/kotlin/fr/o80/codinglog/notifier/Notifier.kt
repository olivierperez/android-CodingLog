package fr.o80.codinglog.notifier

import android.app.NotificationChannelGroup
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import fr.o80.codinglog.CodingLog
import fr.o80.codinglog.R
import fr.o80.codinglog.ui.CodingLogActivity

class Notifier(
    private val context: Context
) {
    private val notificationManager = NotificationManagerCompat.from(context)

    fun notify(
        category: String,
        title: String,
        parameters: Map<String, String>?
    ) {
        if (!allowedToShowNotifications()) {
            Log.w("CodingLog", "Not allowed to show notifications!")
            return
        }

        NotificationBuffer.put(category, title, parameters)

        createGroup(
            id = CodingLog.NOTIFICATION_GROUP_ID,
            name = CodingLog.NOTIFICATION_GROUP_NAME
        )
        createNotifications(
            categories = NotificationBuffer.categories
        )
    }

    private fun createNotifications(categories: List<Category>) {
        categories.forEach { category ->
            val notificationChannelId = "CodingLog-${category.title}"
            createChannel(
                id = notificationChannelId,
                groupId = CodingLog.NOTIFICATION_GROUP_ID,
                name = category.title
            )
            createNotification(
                channelId = notificationChannelId,
                category = category.title,
                notifications = category.notifications
            )
        }
    }

    private fun createGroup(
        id: String,
        name: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager
                .createNotificationChannelGroup(NotificationChannelGroup(id, name))
        }
    }

    private fun createChannel(
        id: String,
        groupId: String,
        name: String
    ) {
        if (notificationManager.getNotificationChannel(id) != null) return

        val channel = NotificationChannelCompat.Builder(
            id,
            NotificationManagerCompat.IMPORTANCE_DEFAULT
        )
            .setName(name)
            .setShowBadge(false)
            .setGroup(groupId)
            .build()

        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(
        channelId: String,
        category: String,
        notifications: List<Notification>
    ) {
        val notificationId = -category.hashCode()
        val notification = NotificationCompat.Builder(context, channelId)
            .setLocalOnly(true)
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentTitle(category)
            .setContentText(notifications.first().title)
            .setStyle(createStyle(notifications))
            .setAutoCancel(true)
            .setNumber(notifications.size)
            .setSubText(notifications.size.toString())
            .setDeleteIntent(clearBufferIntent())
            .setContentIntent(openLogActivity())

        notificationManager.notify(notificationId, notification.build())
    }

    private fun openLogActivity(): PendingIntent {
        return PendingIntent.getActivity(
            context,
            3,
            Intent(context, CodingLogActivity::class.java),
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun clearBufferIntent(): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            1,
            Intent(context, ClearNotificationsBufferReceiver::class.java),
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createStyle(notifications: List<Notification>): NotificationCompat.Style {
        return NotificationCompat.InboxStyle()
            .let { style ->
                notifications.forEach { notification ->
                    style.addLine(notification.title)
                }
                style
            }
    }

    private fun allowedToShowNotifications(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notificationManager.areNotificationsEnabled()
        } else {
            true
        }
    }
}