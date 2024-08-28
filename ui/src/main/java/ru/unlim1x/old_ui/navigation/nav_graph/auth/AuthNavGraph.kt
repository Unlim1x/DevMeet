package ru.unlim1x.old_ui.navigation.nav_graph.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.unlim1x.old_ui.navigation.compose_screen_wrapper.NavigationScreen
import ru.unlim1x.old_ui.screens.auth_code_input_screen.AuthCodeInputScreen
import ru.unlim1x.old_ui.screens.auth_phone_input_screen.AuthPhoneInputScreen
import ru.unlim1x.old_ui.screens.auth_profile_screen.AuthProfileScreen

@Composable
internal fun AuthNavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = AuthNavGraphNodes.PhoneNode.route) {

        composable(
            route = AuthNavGraphNodes.PhoneNode.route,
        ) {
            AuthPhoneInputScreen(
                navController = navController
            )

        }
        composable(
            route = AuthNavGraphNodes.CodeNode.route + "/{code}/{number}",
            arguments = listOf(
                navArgument("code") { type = NavType.StringType },
                navArgument("number") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val phone = backStackEntry.arguments?.getString("number")
            val code = backStackEntry.arguments?.getString("code")

            if (phone?.isEmpty() == false && code?.isEmpty() == false) {
                AuthCodeInputScreen(
                    navController = navController,
                    phone = phone,
                    code = code
                )
            }


        }
        composable(
            route = AuthNavGraphNodes.MainGraphNode.route,
        ) {
            NavigationScreen()

        }

        composable(
            route = AuthNavGraphNodes.ProfileNode.route,
        ) {
            AuthProfileScreen(navController = navController)
        }


    }


}