package ru.unlim1x.wb_project.ui.navigation

import ru.unlim1x.wb_project.R

sealed class NavGraphElements(
    val route: String,
    val title: String,
    val iconId: Int
) {
    data object Meeting : NavGraphElements(
        route = "Встречи",
        title = "Встречи",
        iconId = R.drawable.nav_meeting
    )

    data object Community : NavGraphElements(
        route = "Сообщества",
        title = "Сообщества",
        iconId = R.drawable.nav_comm2
    )

    data object More : NavGraphElements(
        route = "Еще",
        title = "Ещё",
        iconId = R.drawable.nav_more
    )

    data object Profile : NavGraphElements(
        route = "Профиль",
        title = "Профиль",
        iconId = R.drawable.nav_more
    )

    data object Elements : NavGraphElements(
        route = "Элементы",
        title = "Атомы и молекулы",
        iconId = R.drawable.nav_more
    )

    data object MyMeetings : NavGraphElements(
        route = "Мои встречи",
        title = "Мои встречи",
        iconId = R.drawable.nav_more
    )


}