package ru.unlim1x.ui.navigation.nav_graph.global

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.unlim1x.wb_project.ui.navigation.compose_screen_wrapper.AuthentificationScreen
import ru.unlim1x.wb_project.ui.navigation.compose_screen_wrapper.NavigationScreen
import ru.unlim1x.wb_project.ui.screens.splash_screen.SplashScreen

@Composable
internal fun MainNavGraph(navController: NavHostController) {


    NavHost(
        navController = navController,
        startDestination = MainNavGraphNodes.SplashScreen.route
    ) {

        composable(
            route = MainNavGraphNodes.SplashScreen.route,
        ) {
            SplashScreen(navController)

        }
        composable(
            route = MainNavGraphNodes.AuthScreen.route,
        ) {
            AuthentificationScreen()

        }
        composable(
            route = MainNavGraphNodes.MainScreen.route,
        ) {
            NavigationScreen()

        }
    }
}