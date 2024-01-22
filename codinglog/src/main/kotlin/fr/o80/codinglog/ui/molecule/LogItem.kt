package fr.o80.codinglog.ui.molecule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fr.o80.codinglog.data.entity.LogInfoEntity
import java.text.SimpleDateFormat

@Composable
internal fun LogItem(
    log: LogInfoEntity,
    modifier: Modifier = Modifier
) {
    val dateTimeFormatter =
        SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.MEDIUM, SimpleDateFormat.MEDIUM)
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = log.title,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = dateTimeFormatter.format(log.createdAt),
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Text(
                text = log.category,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}
