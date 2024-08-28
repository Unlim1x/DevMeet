package ru.unlim1x.ui.uiKit.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme


internal sealed class AppBarMenuItems(
    val icon: ImageVector?,
    val description: String,
    val tint: Color = Color.Black
) {
    data object BackArrow : AppBarMenuItems(
        icon = Icons.AutoMirrored.Filled.ArrowBack,
        description = "Назад"
    )

    data object Edit : AppBarMenuItems(icon = Icons.Filled.Edit, description = "Редактировать")
    data object Add : AppBarMenuItems(Icons.Filled.Add, description = "Добавить")
    data object GoCheck : AppBarMenuItems(
        Icons.Filled.Check,
        description = "Иду",
        tint = DevMeetTheme.colorScheme.brandDefault
    )
}

