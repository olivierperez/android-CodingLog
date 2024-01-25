package fr.o80.codinglog.ui

import android.content.Context
import fr.o80.codinglog.domain.ObserveAllLogsUseCase
import fr.o80.codinglog.domain.model.LogInfo
import fr.o80.codinglog.ui.model.CategoryUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

internal class LogsListViewModel(
    context: Context
) {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val observeAllLogs = ObserveAllLogsUseCase(context)

    private val selectedCategories = MutableStateFlow<Set<String>>(emptySet())

    val state: StateFlow<LogsListState> =
        combine(
            observeAllLogs()
                .onEach { (_, categories) ->
                    if (selectedCategories.value.isEmpty()) {
                        selectedCategories.update { categories.toSet() }
                    }
                },
            selectedCategories
        ) { (logInfoEntities, categories), selectedCategories ->
            LogsListState(
                logs = logInfoEntities.filter { it.category in selectedCategories },
                categories = categories.map { CategoryUi(it, it in selectedCategories) })
        }
            .stateIn(
                scope = scope,
                started = SharingStarted.Lazily,
                initialValue = LogsListState(emptyList(), emptyList())
            )

    fun onCategoryClick(category: CategoryUi) {
        selectedCategories.update { selected ->
            when {
                category.title in selected -> selected - category.title
                else -> selected + category.title
            }
        }
    }
}

internal data class LogsListState(
    val logs: List<LogInfo>,
    val categories: List<CategoryUi>
)