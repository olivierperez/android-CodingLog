package fr.o80.codinglog.ui.molecule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.o80.codinglog.ui.model.CategoryUi

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun CategoryFilters(
    categories: List<CategoryUi>,
    onCategoryClick: (CategoryUi) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            FilterChip(
                onClick = { onCategoryClick(category) },
                label = { Text(category.title) },
                selected = category.selected,
                leadingIcon = { ChipIcon(category) }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ChipIcon(category: CategoryUi) {
    if (category.selected) {
        Icon(
            imageVector = Icons.Filled.Done,
            contentDescription = "Done icon",
            modifier = Modifier.size(FilterChipDefaults.IconSize)
        )
    } else {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Done icon",
            modifier = Modifier.size(FilterChipDefaults.IconSize)
        )
    }
}