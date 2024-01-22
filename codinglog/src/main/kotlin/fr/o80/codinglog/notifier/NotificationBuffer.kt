package fr.o80.codinglog.notifier

internal object NotificationBuffer {

    private val _categories = mutableMapOf<String, Category>()
    val categories: List<Category> get() = _categories.values.toList()

    fun put(category: String, title: String, parameters: Map<String, String>?) {
        synchronized(_categories) {
            _categories[category] = _categories[category]
                ?.with(Notification(title, parameters))
                ?: Category(category, listOf(Notification(title, parameters)))
        }
    }

    fun clear() {
        synchronized(_categories) {
            _categories.clear()
        }
    }

    fun clearCategory(category: String) {
        synchronized(_categories) {
            _categories.remove(category)
        }
    }
}

internal data class Category(
    val title: String,
    val notifications: List<Notification>
) {
    fun with(notification: Notification): Category {
        return Category(title, notifications + notification)
    }
}

internal data class Notification(
    val title: String,
    val parameters: Map<String, String>?
)
