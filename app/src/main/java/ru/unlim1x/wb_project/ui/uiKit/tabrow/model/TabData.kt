package ru.unlim1x.wb_project.ui.uiKit.tabrow.model

import androidx.compose.runtime.Composable

data class TabData(
    val text: String,
    val screen: @Composable () -> Unit
)
