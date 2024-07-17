package ru.unlim1x.wb_project.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import ru.unlim1x.wb_project.AppBarMenuItems
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.theme.Wb_projectTheme
import ru.unlim1x.wb_project.ui.uiKit.avatar.state.UserAvatarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    header: String,
    backIcon: Boolean,
    backIconAction: () -> Unit,
    animate: Boolean = true,
    actionMenuItem: AppBarMenuItems? = null,
    actionMenu: (() -> Unit)? = null
) {
    var topBarAnimation by remember { mutableStateOf(false) }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Wb_projectTheme.colorScheme.neutralWhite,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            TopAppBarTitle(header = header)
        },
        navigationIcon = {
            if (backIcon) {
                when(animate){
                    true -> {
                        AnimatedBackArrow(visible = topBarAnimation) {
                            backIconAction()
                        }
                    }
                    false -> {
                        StaticBackArrow {
                            backIconAction()
                        }
                    }
                }
            }
        },
        actions = {
            actionMenu?.let { action ->
                if (actionMenuItem != null) {
                    AnimatedAction(item = actionMenuItem, visible = topBarAnimation) {
                        action()
                    }
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
        style = Wb_projectTheme.typography.subheading1
    )
}

@Composable
private fun AnimatedBackArrow(visible: Boolean, action: () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            animationSpec = tween(durationMillis = 500),
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
                    contentDescription = item.description
                )
            }
        }
    }
}