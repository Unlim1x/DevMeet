package ru.unlim1x.wb_project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.wb_project.ui.screens.SplashScreen

@Composable
fun MainNavGraph(navController: NavHostController) {


    NavHost(navController = navController, startDestination = MainNavGraphNodes.SplashScreen.route) {

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