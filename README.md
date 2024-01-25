# Android - Coding Log

Coding Log wants to be a tools for you to debug your developments quality by showing whatever you're
app is not showing. It may help QA to better understand when the invisible stuff happens.

For instance, do you know the exact moment an event has been sent to the analytics server?
With Coding Log you will see the invisible by showing an Android Notification when it occurs.

## How-to

### Report an event

Report the event to `CodingLog`, it will be the main entry point.

```kotlin
    val codingLog = CodingLog(context)
    // ...
    codingLog.report(
        category = "analytics",
        title = "Password updated",
        parameters = mapOf("from" to "profile")
    )
```

### Open the list of all events

```kotlin
    context.startActivity(CodingLog.intent(context))
```

### Show the full screen of events list

```kotlin
LogsListScreen(
    modifier = Modifier.fillMaxSize()
)
```

## ROADMAP

- Show parameters of events
- Clear the database
- Have a no-op implementation
- Show the list of events only
