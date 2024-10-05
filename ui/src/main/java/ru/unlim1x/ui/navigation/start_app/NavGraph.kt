package ru.unlim1x.ui.navigation.start_app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.unlim1x.ui.screens.aka_splash_screen.SplashScreen
import ru.unlim1x.ui.screens.event_detailed.DetailedEventScreen
import ru.unlim1x.ui.screens.event_detailed.DetailedEventScreenViewModel
import ru.unlim1x.ui.screens.main_screen.MainScreen
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
            route = StartAppNavGraphNodes.Main.route,
        ) {
            MainScreen(navController)

        }
        composable(
            route = StartAppNavGraphNodes.DetailedEvent.routeId(),
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            val viewModel: DetailedEventScreenViewModel = koinViewModel(
                parameters = { parametersOf(id) }
            )
            DetailedEventScreen(viewModel = viewModel)

        }
    }
}