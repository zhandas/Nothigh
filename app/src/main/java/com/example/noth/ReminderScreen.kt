package com.example.noth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun ReminderScreen(notificationService: NotificationService) {
    var message by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf(1f) }
    var isReminderSet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Текст напоминания") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isReminderSet
        )

        Text(
            text = "Время до напоминания: ${minutes.toInt()} мин.",
            style = MaterialTheme.typography.bodyLarge
        )

        Slider(
            value = minutes,
            onValueChange = { minutes = it },
            valueRange = 1f..60f,
            steps = 59,
            enabled = !isReminderSet,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { isReminderSet = true },
            enabled = message.isNotBlank() && !isReminderSet,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Установить напоминание")
        }

        if (isReminderSet) {
            LaunchedEffect(Unit) {
                delay(TimeUnit.MINUTES.toMillis(minutes.toLong()))
                notificationService.showNotification(message)
                isReminderSet = false
            }

            Text(
                text = "Напоминание будет показано через ${minutes.toInt()} мин.",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
