package ru.unlim1x.wb_project.ui.navigation

import android.annotation.SuppressLint
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import ru.unlim1x.wb_project.R


sealed class NavGraphElements(
    val route: String,
    val iconId: Int
) {

    data object Init:NavGraphElements(
        route = "Enter",
        iconId = R.drawable.nav_meeting
    )
    data object Meeting : NavGraphElements(
        route = "Встречи",
        iconId = R.drawable.nav_meeting
    )

    data object Community : NavGraphElements(
        route = "Сообщества",
        iconId = R.drawable.nav_comm2
    )

    data object More : NavGraphElements(
        route = "Еще",
        iconId = R.drawable.nav_more
    )

    data object Profile : NavGraphElements(
        route = "Профиль",
        iconId = R.drawable.nav_more
    )

    data object Elements : NavGraphElements(
        route = "Элементы",
        iconId = R.drawable.nav_more
    )

    data object MyMeetings : NavGraphElements(
        route = "Мои встречи",
        iconId = R.drawable.nav_more
    )


}