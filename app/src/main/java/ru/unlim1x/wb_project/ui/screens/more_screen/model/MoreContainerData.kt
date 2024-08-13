package ru.unlim1x.wb_project.ui.screens.more_screen.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

internal data class MoreContainerData(
    @DrawableRes
    val iconId: Int,
    @StringRes
    val textId: Int,
    val navigationRoute: String? = null,
)
