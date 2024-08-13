package ru.unlim1x.wb_project.ui.uiKit.tab_layout.model

import androidx.compose.runtime.Composable

internal data class TabData(
    val text: String,
    val screen: @Composable () -> Unit
)
