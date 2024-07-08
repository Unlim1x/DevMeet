package ru.unlim1x.wb_project.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.unlim1x.wb_project.ui.screens.CommunityDetailedScreen
import ru.unlim1x.wb_project.ui.screens.CommunityScreen
import ru.unlim1x.wb_project.ui.screens.ElementsScreen1
import ru.unlim1x.wb_project.ui.screens.MeetingDetailedScreen
import ru.unlim1x.wb_project.ui.screens.MeetingScreen
import ru.unlim1x.wb_project.ui.screens.MoreScreen
import ru.unlim1x.wb_project.ui.screens.MyMeetingScreen
import ru.unlim1x.wb_project.ui.screens.ProfileScreen
import ru.unlim1x.wb_project.ui.uiKit.cards.TimeAndPlace
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Event
import ru.unlim1x.wb_project.ui.uiKit.tabrow.model.TabData

@Composable
fun BottomNavGraph(navController: NavHostController, bottomPadding: Dp) {


    NavHost(navController = navController, startDestination = NavGraphNodes.MeetingRoot.route){
        navigation(startDestination = NavGraphNodes.MeetingRoot.Meeting.route, route = NavGraphNodes.MeetingRoot.route){
            composable(
                route = NavGraphNodes.MeetingRoot.Meeting.route,
            ) {
                Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                    MeetingScreen(
                        navController = navController
                    )
                }
            }
            composable(
                route = NavGraphNodes.MeetingRoot.MeetingDetailed.route+"/{id}/{name}",
                arguments = listOf(navArgument("name") { type = NavType.StringType }, navArgument("id") { type = NavType.IntType })
            ) {backStackEntry ->
                val eventName = backStackEntry.arguments?.getString("name")
                val eventId = backStackEntry.arguments?.getInt("id")
                if (eventName != null && eventId != null) {
                    Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                        MeetingDetailedScreen(
                            navController = navController,
                            eventName = eventName,
                            eventId = eventId
                        )
                    }
                }

            }
        }

        navigation(startDestination = NavGraphNodes.CommunityRoot.Community.route,
            route = NavGraphNodes.CommunityRoot.route){
            composable(
                route = NavGraphNodes.CommunityRoot.Community.route,
            ) {
                Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                    CommunityScreen(
                        navController = navController
                    )
                }
            }

            composable(
                route = NavGraphNodes.CommunityRoot.CommunityDetailed.route+"/{id}/{name}",
                arguments = listOf(navArgument("name") { type = NavType.StringType }, navArgument("id") { type = NavType.IntType })
            ) {backStackEntry ->
                val communityName = backStackEntry.arguments?.getString("name")
                val communityId = backStackEntry.arguments?.getInt("id")
                if (communityName != null && communityId != null) {
                    Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                        CommunityDetailedScreen(
                            navController = navController,
                            communityName = communityName,
                            communityId = communityId
                        )
                    }
                }

            }

            composable(
                route = NavGraphNodes.CommunityRoot.CommunityDetailed.MeetingDetailed.route+"/{id}/{name}",
                arguments = listOf(navArgument("name") { type = NavType.StringType }, navArgument("id") { type = NavType.IntType })
            ) {backStackEntry ->
                val eventName = backStackEntry.arguments?.getString("name")
                val eventId = backStackEntry.arguments?.getInt("id")
                if (eventName != null && eventId != null) {
                    Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                        MeetingDetailedScreen(
                            navController = navController,
                            eventName = eventName,
                            eventId = eventId
                        )
                    }
                }

            }
        }

        navigation(
            startDestination = NavGraphNodes.MoreRoot.More.route,
            route = NavGraphNodes.MoreRoot.route
        ) {
            composable(route = NavGraphNodes.MoreRoot.More.route){
                Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                    MoreScreen(
                        navController = navController
                    )
                }
            }
            composable(route = NavGraphNodes.MoreRoot.Profile.route){
                Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                    ProfileScreen(
                        navController = navController
                    )
                }
            }
            composable(route = NavGraphNodes.MoreRoot.MyMeetings.route){
                Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                    MyMeetingScreen(
                        navController = navController
                    )
                }
            }
            composable(route = NavGraphNodes.MoreRoot.Elements.route){
                Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                    ElementsScreen1()
                }
            }
            composable(
                route = NavGraphNodes.MoreRoot.MeetingDetailed.route+"/{id}/{name}",
                arguments = listOf(navArgument("name") { type = NavType.StringType }, navArgument("id") { type = NavType.IntType })
            ) {backStackEntry ->
                val eventName = backStackEntry.arguments?.getString("name")
                val eventId = backStackEntry.arguments?.getInt("id")
                if (eventName != null && eventId != null) {
                    Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                        MeetingDetailedScreen(
                            navController = navController,
                            eventName = eventName,
                            eventId = eventId
                        )
                    }
                }

            }
        }






    }

}
