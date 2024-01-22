package fr.o80.codinglog.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.o80.codinglog.data.entity.LogInfoEntity
import fr.o80.codinglog.ui.model.CategoryUi
import fr.o80.codinglog.ui.molecule.CategoryFilters
import fr.o80.codinglog.ui.molecule.LogItem
import fr.o80.codinglog.ui.theme.CodingLogThemePreview
import java.util.Date

@Composable
internal fun LogsListScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val viewModel = remember { LogsListViewModel(context) }

    val state by viewModel.state.collectAsState()

    LogsList(
        logs = state.logs,
        categories = state.categories,
        onCategoryClick = viewModel::onCategoryClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LogsList(
    logs: List<LogInfoEntity>,
    categories: List<CategoryUi>,
    onCategoryClick: (CategoryUi) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            CategoryFilters(
                categories = categories,
                onCategoryClick = onCategoryClick
            )
        }
        items(
            items = logs,
            contentType = { "log" },
            key = { it.uid }
        ) { log ->
            LogItem(
                log = log,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItemPlacement()
            )
        }
    }
}

@Preview
@Composable
private fun LogsListPreview() {
    CodingLogThemePreview {
        LogsList(
            logs = listOf(
                LogInfoEntity(
                    uid = 1,
                    createdAt = Date(),
                    category = "Example",
                    title = "Example Screen Viewed",
                    parameters = null
                ),
                LogInfoEntity(
                    uid = 2,
                    createdAt = Date(1337),
                    category = "Demo",
                    title = "Somthing Else",
                    parameters = null
                ),
            ),
            categories = listOf(
                CategoryUi("Demo", true),
                CategoryUi("Example", true),
                CategoryUi("Third Parties", false),
            ),
            onCategoryClick = {}
        )
    }
}
