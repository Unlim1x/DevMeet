package ru.unlim1x.wb_project.ui.screens

import android.view.SoundEffectConstants
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.wb_project.ui.navigation.BottomNavGraph
import ru.unlim1x.wb_project.ui.navigation.NavGraphNodes
import ru.unlim1x.wb_project.ui.theme.Wb_projectTheme
import ru.unlim1x.wb_project.ui.uiKit.theme.NoRippleTheme
import ru.unlim1x.wb_project.ui.uiKit.theme.PrimaryColorRippleTheme

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomBar(navController = navController) }) {
        BottomNavGraph(navController = navController, it.calculateBottomPadding())
    }
}

@Composable
fun BottomBar(navController: NavHostController) {

    val roots = listOf(
        NavGraphNodes.MeetingRoot,
        NavGraphNodes.CommunityRoot,
        NavGraphNodes.MoreRoot
    )

    val view = LocalView.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        BottomNavigation(
            backgroundColor = Wb_projectTheme.colorScheme.neutralWhite,
            elevation = 10.dp,
            modifier = Modifier.height(64.dp)
        ) {

            roots.forEachIndexed { _, screen ->
                val selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true
                BottomNavigationItem(
                    icon = {
                        if (!selected)
                            CompositionLocalProvider(LocalRippleTheme provides PrimaryColorRippleTheme) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = screen.iconId),
                                    contentDescription = screen.route,
                                    modifier = Modifier.height(25.dp)
                                )
                            }


                    },
                    selected = selected,
                    label = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(screen.label, style = Wb_projectTheme.typography.bodyText1)
                            Canvas(modifier = Modifier.padding(top = 4.dp)) {
                                drawCircle(
                                    color = Wb_projectTheme.colorScheme.neutralActive,
                                    radius = 4f,
                                    style = Fill
                                )
                            }
                        }
                    },
                    alwaysShowLabel = false,
                    onClick = {
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },

                    unselectedContentColor = Color(0xFF2E3A59)

                )
            }
        }
    }
}