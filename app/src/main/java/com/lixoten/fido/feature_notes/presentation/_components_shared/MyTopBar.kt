package com.lixoten.fido.feature_notes.presentation._components_shared

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lixoten.fido.R

@Composable
fun MyTopBar(
    screenTitle: String,
    canNavigateUp: Boolean,
    navigateUp: () -> Unit = { },
    isGridLayout: Boolean = false,
    canAdd: Boolean = false,
    onAddRecord: () -> Unit = { },
    onToggleLayout: () -> Unit = { },
    onToggleSection: () -> Unit = { },
    onToggleSearch: () -> Unit = { },
    hasMenu: Boolean = false,
    onNavigationIconClick: () -> Unit
) {
        TopAppBar(
            title = { Text(text = screenTitle) },
            navigationIcon = {
                if (canNavigateUp) {
                    IconButton(
                        onClick = navigateUp
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_arrow)
                        )
                    }
                }
                if (hasMenu) {
                    IconButton(onClick = onNavigationIconClick) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Toggle drawer"
                        )
                    }
                }
            },
            actions = {
                if (canAdd) {
                    IconButton(
                        onClick = onAddRecord
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_note_icon_descr),
                            //tint = Color.Red
                            //modifier = Modifier.size(24.dp),
                        )
                    }

                    IconButton(
                        onClick = onToggleLayout
                    ) {
                        Icon(
                            imageVector = if (isGridLayout) Icons.Default.GridView else Icons.Default.ViewList,
                            // Danger fix me
                            contentDescription = stringResource(R.string.add_note_icon_descr),
                        )
                    }
                    IconButton(
                        onClick = onToggleSection
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = stringResource(R.string.sort_icon_descr),
                        )
                    }
                    IconButton(
                        onClick = onToggleSearch
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.sort_icon_descr),
                        )
                    }
                }
            },

            elevation = 8.dp
        )
}