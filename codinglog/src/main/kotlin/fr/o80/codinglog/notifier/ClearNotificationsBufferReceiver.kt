package fr.o80.codinglog.notifier

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

internal class ClearNotificationsBufferReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        NotificationBuffer.clear()
    }
}