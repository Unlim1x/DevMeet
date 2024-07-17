package ru.unlim1x.wb_project.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextOverflow
import ru.unlim1x.wb_project.AppBarMenuItems
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme

private const val ANIMATION_DURATION = 300
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    header: String = "",
    backIconIsVisible: Boolean = false,
    backIconAction: (() -> Unit)? = null,
    animate: Boolean = true,
    actionMenuItem: AppBarMenuItems? = null,
    actionMenu: (() -> Unit)? = null
) {
    var topBarAnimation by remember { mutableStateOf(false) }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DevMeetTheme.colorScheme.neutralWhite,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            TopAppBarTitle(header = header)
        },
        navigationIcon = {
            if (backIconIsVisible) {
                when(animate){
                    true -> {
                        AnimatedBackArrow(visible = topBarAnimation) {
                            backIconAction?.invoke()
                            }
                        }

                    false -> {
                        StaticBackArrow {
                            backIconAction?.invoke()
                        }
                    }
                }
            }
        },
        actions = {

                if (actionMenuItem != null) {
                    AnimatedAction(item = actionMenuItem, visible = topBarAnimation) {
                        actionMenu?.invoke()
                    }
                }


        }
    )

    LaunchedEffect(key1 = Unit) {
        topBarAnimation = true
    }

}

@Composable
private fun TopAppBarTitle(header:String){
    Text(
        header,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = DevMeetTheme.typography.subheading1
    )
}

@Composable
private fun AnimatedBackArrow(visible: Boolean, action: () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            animationSpec = tween(durationMillis = ANIMATION_DURATION),
            initialOffsetX = { -it })
    ) {
        IconButton(onClick = { action() }) {
            AppBarMenuItems.BackArrow.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = AppBarMenuItems.BackArrow.description
                )
            }
        }
    }
}
@Composable
private fun StaticBackArrow(action: () -> Unit){
    IconButton(onClick = { action() }) {
        AppBarMenuItems.BackArrow.icon?.let {
            Icon(
                imageVector = it,
                contentDescription = AppBarMenuItems.BackArrow.description
            )
        }
    }
}

@Composable
private fun AnimatedAction(item: AppBarMenuItems, visible: Boolean, action: () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            animationSpec = tween(durationMillis = 500),
            initialOffsetX = { it })
    ) {
        IconButton(onClick = {
            action()
        }) {
            item.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = item.description,
                    tint = item.tint
                )
            }
        }
    }
}