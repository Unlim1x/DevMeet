package ru.unlim1x.wb_project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.unlim1x.wb_project.ui.screens.AuthCodeInputScreen
import ru.unlim1x.wb_project.ui.screens.NavigationScreen
import ru.unlim1x.wb_project.ui.screens.AuthPhoneInputScreen
import ru.unlim1x.wb_project.ui.screens.AuthProfileScreen

@Composable
fun AuthNavGraph(navController: NavHostController) {


    NavHost(navController = navController, startDestination = AuthNavGraphNodes.PhoneNode.route) {

        composable(
            route = AuthNavGraphNodes.PhoneNode.route,
        ) {
             AuthPhoneInputScreen(
                    navController = navController
                )

        }
        composable(
            route = AuthNavGraphNodes.CodeNode.route +"/{code}/{number}",
            arguments = listOf(
                navArgument("code") { type = NavType.StringType },
                navArgument("number") { type = NavType.StringType }
            )
        ) {backStackEntry->
            val phone = backStackEntry.arguments?.getString("number")
            val code = backStackEntry.arguments?.getString("code")

            phone?.let{phone->
                code?.let{code->
                    AuthCodeInputScreen(
                        navController = navController,
                        phone = phone,
                        code = code
                    )

            }

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