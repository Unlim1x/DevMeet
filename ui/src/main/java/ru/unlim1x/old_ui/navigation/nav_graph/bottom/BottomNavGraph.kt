package ru.unlim1x.old_ui.navigation.nav_graph.bottom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.unlim1x.old_ui.screens.community_detailed_screen.CommunityDetailedScreen
import ru.unlim1x.old_ui.screens.community_screen.CommunityScreen
import ru.unlim1x.old_ui.screens.developer_screen.DeveloperScreen1
import ru.unlim1x.old_ui.screens.meeting_detailed.MeetingDetailedScreen
import ru.unlim1x.old_ui.screens.meeting_screen.MeetingScreen
import ru.unlim1x.old_ui.screens.more_screen.MoreScreen
import ru.unlim1x.old_ui.screens.my_meetings.MyMeetingScreen
import ru.unlim1x.old_ui.screens.profile_screen.ProfileScreen

@Composable
internal fun BottomNavGraph(navController: NavHostController, bottomPadding: Dp) {


    NavHost(navController = navController, startDestination = NavGraphNodes.MeetingRoot.route) {
        navigation(
            startDestination = NavGraphNodes.MeetingRoot.Meeting.route,
            route = NavGraphNodes.MeetingRoot.route
        ) {
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
                route = NavGraphNodes.MeetingRoot.MeetingDetailed.route + "/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->

                val eventId = backStackEntry.arguments?.getInt("id")
                if (eventId != null) {
                    Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                        MeetingDetailedScreen(
                            navController = navController,
                            eventId = eventId
                        )
                    }
                }

            }
        }

        navigation(
            startDestination = NavGraphNodes.CommunityRoot.Community.route,
            route = NavGraphNodes.CommunityRoot.route
        ) {
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
                route = NavGraphNodes.CommunityRoot.CommunityDetailed.route + "/{id}/{name}",
                arguments = listOf(
                    navArgument("name") { type = NavType.StringType },
                    navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
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
                route = NavGraphNodes.CommunityRoot.CommunityDetailed.MeetingDetailed.route + "/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getInt("id")
                if (eventId != null) {
                    Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                        MeetingDetailedScreen(
                            navController = navController,
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
            composable(route = NavGraphNodes.MoreRoot.More.route) {
                Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                    MoreScreen(
                        navController = navController
                    )
                }
            }
            composable(route = NavGraphNodes.MoreRoot.Profile.route) {
                Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                    ProfileScreen(
                        navController = navController
                    )
                }
            }
            composable(route = NavGraphNodes.MoreRoot.MyMeetings.route) {
                Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                    MyMeetingScreen(
                        navController = navController
                    )
                }
            }
            composable(route = NavGraphNodes.MoreRoot.Elements.route) {
                Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                    DeveloperScreen1()
                }
            }
            composable(
                route = NavGraphNodes.MoreRoot.MeetingDetailed.route + "/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getInt("id")
                if (eventId != null) {
                    Box(modifier = Modifier.padding(bottom = bottomPadding)) {
                        MeetingDetailedScreen(
                            navController = navController,
                            eventId = eventId
                        )
                    }
                }

            }
        }


    }

}
