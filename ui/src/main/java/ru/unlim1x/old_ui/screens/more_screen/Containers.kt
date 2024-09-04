package ru.unlim1x.old_ui.screens.more_screen

import ru.unlim1x.old_ui.navigation.nav_graph.bottom.NavGraphNodes
import ru.unlim1x.old_ui.screens.more_screen.model.MoreContainerData
import ru.unlim1x.ui.R

internal class Containers {
    companion object {
        private val myMeetings =
            MoreContainerData(
                iconId = R.drawable.icon_meeting,
                textId = R.string.my_meetings,
                navigationRoute = NavGraphNodes.MoreRoot.MyMeetings.route
            )
        private val theme =
            MoreContainerData(iconId = R.drawable.icon_theme, textId = R.string.theme_string)
        private val notification =
            MoreContainerData(
                iconId = R.drawable.icon_notification,
                textId = R.string.notifications
            )
        private val safety =
            MoreContainerData(iconId = R.drawable.icon_safety, textId = R.string.safety)
        private val memory =
            MoreContainerData(iconId = R.drawable.icon_res, textId = R.string.memory_res_string)
        private val help = MoreContainerData(iconId = R.drawable.icon_help, textId = R.string.help)
        private val invite =
            MoreContainerData(iconId = R.drawable.icon_invite, textId = R.string.invite_friend)

        val listOfContainers =
            listOf(myMeetings, theme, notification, safety, memory, help, invite)
    }
}