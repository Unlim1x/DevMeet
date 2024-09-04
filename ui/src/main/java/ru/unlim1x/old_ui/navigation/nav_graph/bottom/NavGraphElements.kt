package ru.unlim1x.old_ui.navigation.nav_graph.bottom

import androidx.annotation.DrawableRes
import ru.unlim1x.ui.R


internal sealed class NavGraphNodes(
    open val route: String,
    val label: String,
    @DrawableRes
    val iconId: Int
) {


    data object MeetingRoot : NavGraphNodes(
        route = "MeetingRoot",
        label = "Встречи",
        iconId = R.drawable.nav_meeting
    ) {
        data object Meeting : NavGraphNodes(
            route = "Meeting",
            label = "Встречи",
            iconId = R.drawable.nav_meeting
        )

        data object MeetingDetailed : NavGraphNodes(
            route = "MeetingDetailed",
            label = "Встречи",
            iconId = R.drawable.nav_meeting
        )
    }

    data object CommunityRoot : NavGraphNodes(
        route = "CommunityRoot",
        label = "Сообщества",
        iconId = R.drawable.nav_comm2
    ) {
        data object Community : NavGraphNodes(
            route = "Community",
            label = "Сообщества",
            iconId = R.drawable.nav_comm2
        )

        data object CommunityDetailed : NavGraphNodes(
            route = "CommunityDetailed",
            label = "Сообщества",
            iconId = R.drawable.nav_comm2
        ) {
            data object MeetingDetailed : NavGraphNodes(
                route = "CommunityMeetingDetailed",
                label = "Встречи",
                iconId = R.drawable.nav_comm2
            )
        }
    }

    data object MoreRoot : NavGraphNodes(
        route = "MoreRoot",
        label = "Еще",
        iconId = R.drawable.nav_more
    ) {
        data object More : NavGraphNodes(
            route = "More",
            label = "Еще",
            iconId = R.drawable.nav_more
        )

        data object Profile : NavGraphNodes(
            route = "Profile",
            label = "Профиль",
            iconId = R.drawable.nav_more
        )

        data object Elements : NavGraphNodes(
            route = "Elements",
            label = "Элементы",
            iconId = R.drawable.nav_more
        )

        data object MyMeetings : NavGraphNodes(
            route = "MyMeetings",
            label = "Мои встречи",
            iconId = R.drawable.nav_more
        )

        data object MeetingDetailed : NavGraphNodes(
            route = "MoreMeetingDetailed",
            label = "Встречи",
            iconId = R.drawable.nav_meeting
        )
    }


}