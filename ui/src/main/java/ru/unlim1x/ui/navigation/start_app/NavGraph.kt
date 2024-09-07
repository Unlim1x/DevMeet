package ru.unlim1x.ui.navigation.start_app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.unlim1x.ui.screens.aka_splash_screen.SplashScreen
import ru.unlim1x.ui.screens.onboarding.Onboarding


@Composable
internal fun StartAppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = StartAppNavGraphNodes.Splash.route
    ) {

        composable(
            route = StartAppNavGraphNodes.Splash.route,
        ) {
            SplashScreen(navController)

        }
        composable(
            route = StartAppNavGraphNodes.Onboarding.route,
        ) {
            Onboarding(navController = navController)

        }
        composable(
            route = StartAppNavGraphNodes.GeoOnboarding.route,
        ) {
            //NavigationScreen()

        }
    }
}