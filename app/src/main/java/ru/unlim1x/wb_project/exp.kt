package ru.unlim1x.wb_project

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.rememberNavController

@Composable
fun Test(actionBack: @Composable ()->Unit, actionMenu:AppBarMenuItems, actionMenuClick: (() -> Unit)?) {

}

@Composable
fun CallTest(){
    Test(actionBack = { rememberNavController().navigateUp() }, actionMenu = AppBarMenuItems.BackArrow, actionMenuClick = null)
}

sealed class AppBarMenuItems(val icon: ImageVector?, val description: String) {
    data object BackArrow : AppBarMenuItems(
        icon = Icons.AutoMirrored.Filled.ArrowBack,
        description = "Назад"
    )
    data object Edit : AppBarMenuItems(icon = Icons.Filled.Edit, description = "Редактировать")
    data object Add : AppBarMenuItems(Icons.Filled.Add, "Добавить")
}

