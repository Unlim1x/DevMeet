package ru.unlim1x.wb_project.ui.navigation

import android.annotation.SuppressLint
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import ru.unlim1x.wb_project.R




sealed class NavGraphNodes(
    open val route: String,
    val label:String,
    val iconId: Int
) {



    data object MeetingRoot : NavGraphNodes(
        route = "Meeting",
        label = "Встречи",
        iconId = R.drawable.nav_meeting
    )

    data object CommunityRoot : NavGraphNodes(
        route = "Community",
        label = "Сообщества",
        iconId = R.drawable.nav_comm2
    )

    data object MoreRoot : NavGraphNodes(
        route = "MoreRoot",
        label = "Еще",
        iconId = R.drawable.nav_more
    ){
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
    }




}
