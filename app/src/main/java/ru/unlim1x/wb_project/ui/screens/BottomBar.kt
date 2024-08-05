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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.koin.androidx.compose.koinViewModel
import ru.unlim1x.wb_project.ui.navigation.NavGraphNodes
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.theme.NoRippleTheme
import ru.unlim1x.wb_project.ui.uiKit.theme.PrimaryColorRippleTheme
import ru.unlim1x.wb_project.ui.viewmodels.bottom_bar.BottomBarEvent
import ru.unlim1x.wb_project.ui.viewmodels.bottom_bar.BottomBarViewModel
import ru.unlim1x.wb_project.ui.viewmodels.bottom_bar.BottomBarViewState

@Composable
fun BottomBar(navController: NavHostController, viewModel: BottomBarViewModel = koinViewModel()) {

    val viewState = viewModel.viewState().collectAsStateWithLifecycle()
    val view = LocalView.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        BottomNavigation(
            backgroundColor = DevMeetTheme.colorScheme.neutralWhite,
            elevation = 10.dp,
            modifier = Modifier.height(64.dp)
        ) {
            when (val state = viewState.value) {
                is BottomBarViewState.Display -> {
                    state.roots.forEachIndexed { _, screen ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    node = screen,
                                    selected = selected,
                                    overengineering = state.overengineering
                                )
                            },
                            selected = selected,
                            label = { Label(screen.label) },
                            alwaysShowLabel = false,
                            onClick = {
                                view.playSoundEffect(SoundEffectConstants.CLICK)
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id){
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    if(currentDestination?.parent?.route != screen.route) {
                                        restoreState = true
                                    }
                                }
                            },

                            unselectedContentColor = Color(0xFF2E3A59)

                        )
                    }
                }

                BottomBarViewState.Init -> {
                    viewModel.obtain(BottomBarEvent.LoadBottomBar)
                }

                else -> throw NotImplementedError("Unexpected state")
            }
        }
    }
}


@Composable
private fun Label(text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text, style = DevMeetTheme.typography.bodyText1)
        Canvas(modifier = Modifier.padding(top = 4.dp)) {
            drawCircle(
                color = DevMeetTheme.colorScheme.neutralActive,
                radius = 4f,
                style = Fill
            )
        }
    }
}

@Composable
private fun Icon(node: NavGraphNodes, selected: Boolean, overengineering: Int) {
    if (!selected)
        CompositionLocalProvider(LocalRippleTheme provides PrimaryColorRippleTheme) {
            Icon(
                imageVector = ImageVector.vectorResource(id = node.iconId),
                contentDescription = node.route,
                modifier = Modifier.height(25.dp)
            )
        }
}

