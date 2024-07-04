package ru.unlim1x.wb_project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.unlim1x.wb_project.ui.screens.CommunityScreen
import ru.unlim1x.wb_project.ui.screens.ElementsScreen1
import ru.unlim1x.wb_project.ui.screens.MeetingScreen
import ru.unlim1x.wb_project.ui.screens.MoreScreen
import ru.unlim1x.wb_project.ui.screens.MyMeetingScreen
import ru.unlim1x.wb_project.ui.screens.ProfileScreen
import ru.unlim1x.wb_project.ui.uiKit.cards.TimeAndPlace
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Event
import ru.unlim1x.wb_project.ui.uiKit.tabrow.PageMeetings
import ru.unlim1x.wb_project.ui.uiKit.tabrow.model.TabData

@Composable
fun BottomNavGraph(navController: NavHostController, bottomPadding: Dp) {

    //TODO: Пример!!, потом данные будут браться из viewmodel'и
    val listOfTags = listOf("Junior", "Python", "Moscow")
    val listEvents: MutableList<Event> = mutableListOf()
    val listEvents2: MutableList<Event> = mutableListOf()
    val listEventsPlanned: MutableList<Event> = mutableListOf()
    val listEventsFinished: MutableList<Event> = mutableListOf()
    for (x in 0..14) {
        listEvents.add(
            index = x,
            element = Event(
                name = "Developer meeting",
                timeAndPlace = TimeAndPlace(place = "Moscow", date = 13, month = 9, year = 2024),
                isFinished = x % 4 == 0,
                tags = listOfTags
            )
        )
        listEventsPlanned.add(
            index = x,
            element = Event(
                name = "Developer meeting",
                timeAndPlace = TimeAndPlace(place = "Moscow", date = 13, month = 9, year = 2024),
                isFinished = false,
                tags = listOfTags
            )
        )
    }
    for (x in 0..2) {
        listEvents2.add(
            index = x, element = Event(
                name = "Developer meeting",
                timeAndPlace = TimeAndPlace(place = "Moscow", date = 13, month = 9, year = 2024),
                isFinished = x % 4 == 0,
                tags = listOfTags
            )
        )
        listEventsFinished.add(
            index = x, element = Event(
                name = "Developer meeting",
                timeAndPlace = TimeAndPlace(place = "Moscow", date = 13, month = 9, year = 2024),
                isFinished = true,
                tags = listOfTags
            )
        )
    }

    val tabsAll = listOf(
        TabData(text = "ВСЕ ВСТРЕЧИ", screen = {
            PageMeetings(
                listEvents = listEvents
            )
        }),
        TabData(text = "АКТИВНЫЕ", screen = {
            PageMeetings(
                listEvents = listEvents2
            )
        }),
    )
    val tabsMy = listOf(
        TabData(text = "ЗАПЛАНИРОВАНО", screen = {
            PageMeetings(
                listEvents = listEventsPlanned
            )
        }),
        TabData(text = "УЖЕ ПРОШЛИ", screen = {
            PageMeetings(
                listEvents = listEventsFinished
            )
        }),
    )

    NavHost(navController = navController, startDestination = NavGraphElements.Meeting.route) {


        composable(
            route = NavGraphElements.Meeting.route,
        ) {
            MeetingScreen(
                bottomPadding = bottomPadding,
                navController = navController,
                tabs = tabsAll
            )
        }
        composable(route = NavGraphElements.Community.route) {

            CommunityScreen()
        }
        composable(route = NavGraphElements.More.route) {
            MoreScreen(bottomPadding = bottomPadding, navController = navController)
        }
        composable(route = NavGraphElements.Profile.route) {
            ProfileScreen(bottomPadding = bottomPadding, navController = navController)
        }
        composable(route = NavGraphElements.Elements.route) {
            ElementsScreen1(bottomPadding = bottomPadding)
        }

        composable(route = NavGraphElements.MyMeetings.route) {
            MyMeetingScreen(
                bottomPadding = bottomPadding,
                navController = navController,
                tabs = tabsMy
            )
        }


    }

}