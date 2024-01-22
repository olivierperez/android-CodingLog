package fr.o80.codinglog

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import fr.o80.codinglog.ui.theme.CodingLogTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodingLogTheme {
                ExampleContent()
            }
        }
    }
}


@Composable
private fun ExampleContent() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val codingLog = remember { CodingLog(context) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            IconButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    context.startActivity(CodingLog.intent(context))
                }
            ) {
                Icon(Icons.Default.List, contentDescription = "All logs")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            CheckNotificationPermission()

            Text(
                text = "Amplitude",
                style = MaterialTheme.typography.headlineMedium
            )

            Button(onClick = {
                scope.launch {
                    codingLog
                        .report(
                            category = "Amplitude",
                            title = "Screen Viewed",
                            parameters = null
                        )
                }
            }) {
                Text("Screen viewed")
            }

            Button(onClick = {
                scope.launch {
                    codingLog
                        .report(
                            category = "Amplitude",
                            title = "Login Button clicked",
                            parameters = mapOf("from" to "example")
                        )
                }
            }) {
                Text("Login Button")
            }

            Text(
                text = "Adjust",
                style = MaterialTheme.typography.headlineMedium
            )

            Button(onClick = {
                scope.launch {
                    codingLog
                        .report(
                            category = "Adjust",
                            title = "Promo clicked",
                            parameters = mapOf("from" to "example")
                        )
                }
            }) {
                Text("Promo Button")
            }
        }
    }
}

@Composable
fun CheckNotificationPermission() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        return
    }

    val context = LocalContext.current
    val hasPostNotificationPermission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.POST_NOTIFICATIONS
    ) == PackageManager.PERMISSION_GRANTED

    if (!hasPostNotificationPermission) {
        Button(
            onClick = {
                context.startActivity(Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                    putExtra("app_package", context.packageName)
                    putExtra("app_uid", context.applicationInfo.uid)
                })
            }
        ) {
            Text("Allow notifications")
        }
    }
}

@Preview
@Composable
private fun ExampleContentPreview() {
    CodingLogTheme {
        ExampleContent()
    }
}