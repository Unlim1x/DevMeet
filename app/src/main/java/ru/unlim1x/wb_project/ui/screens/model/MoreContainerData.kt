package ru.unlim1x.wb_project.ui.screens.model

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes

data class MoreContainerData(
    @DrawableRes
    val iconId: Int,
    @StringRes
    val textId: Int,
    val navigationRoute: String? = null,
)

