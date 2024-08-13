package ru.unlim1x.wb_project.ui.screens.more_screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.lim1x.domain.models.User
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.navigation.nav_graph.bottom.NavGraphNodes
import ru.unlim1x.wb_project.ui.screens.more_screen.model.MoreContainerData
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.avatar.UserAvatar
import ru.unlim1x.wb_project.ui.uiKit.topbar.TopBar


private const val PROFILE_CARD_VERTICAL_PADDING = 8
private const val COLUMN_HORIZONTAL_PADDING = 16

@Composable
internal fun MoreScreen(
    navController: NavController,
    viewModel: MoreScreenViewModel = koinViewModel()
) {

    val viewState by viewModel.viewState().collectAsStateWithLifecycle()
    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(header = stringResource(id = R.string.more))

        }) {
        val modifier = Modifier.padding(top = it.calculateTopPadding())

        when (viewState) {
            is MoreScreenViewState.Display -> {
                Log.e("MoreScreen", "Display")
                MoreScreenBody(
                    navController = navController,
                    modifier = modifier,
                    user = (viewState as MoreScreenViewState.Display).user,
                    containerList = (viewState as MoreScreenViewState.Display).containerList
                )
            }

            MoreScreenViewState.Init -> {

            }

        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.obtain(MoreScreenEvent.OpenScreen)
    }
}

@Composable
private fun MoreScreenBody(
    navController: NavController,
    modifier: Modifier,
    user: User,
    containerList: List<MoreContainerData>
) {
    val firstContainer = 0
    val lastContainer = containerList.size - 1
    val containerWithDivider = 4

    LazyColumn(
        modifier = modifier.padding(horizontal = COLUMN_HORIZONTAL_PADDING.dp)
    ) {
        item {
            MoreContainer(
                user = user,
                modifier = Modifier.padding(vertical = PROFILE_CARD_VERTICAL_PADDING.dp)
            ) {
                navController.navigate(NavGraphNodes.MoreRoot.Profile.route)
            }
        }
        itemsIndexed(containerList) { index, item ->
            val bottomPadding = when (index) {
                firstContainer -> 16.dp
                else -> 8.dp
            }
            val topPadding = when (index) {
                firstContainer -> 8.dp
                lastContainer -> 8.dp
                else -> 0.dp
            }
            when (index) {
                containerWithDivider -> {
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                }
            }
            MoreContainer(
                moreContainerData = item,
                modifier = Modifier.padding(top = topPadding, bottom = bottomPadding)
            ) {
                item.navigationRoute?.let { route ->
                    navController.navigate(route = route)
                }
            }
        }
    }
}

@Composable
private fun MoreContainer(
    moreContainerData: MoreContainerData,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    @Composable
    fun MoreContainerWrapper() {
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = moreContainerData.iconId),
                contentDescription = stringResource(moreContainerData.textId),
                modifier = Modifier
                    .height(20.dp)
                    .width(18.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(moreContainerData.textId),
                style = DevMeetTheme.typography.bodyText1
            )
        }
    }

    Row(modifier = modifier
        .requiredHeightIn(min = 40.dp, max = 50.dp)
        .clickable { onClick() }
        .fillMaxWidth()
        .padding(end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        MoreContainerWrapper()
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.icon_right_arrow),
            contentDescription = stringResource(moreContainerData.textId),
            modifier = Modifier.height(20.dp)
        )
    }

}

@Composable
private fun MoreContainer(user: User, modifier: Modifier = Modifier, onClick: () -> Unit) {
    @Composable
    fun MoreContainerWrapper(Avatar: @Composable () -> Unit) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(modifier = Modifier.size(50.dp)) {
                Avatar()
            }

            Spacer(modifier = Modifier.size(8.dp))
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = user.name, style = DevMeetTheme.typography.bodyText1)
                Text(text = user.phone, style = DevMeetTheme.typography.metadata1)
            }
        }
    }

    Row(modifier = modifier
        .requiredHeightIn(min = 40.dp, max = 50.dp)
        .clickable { onClick() }
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {

        MoreContainerWrapper {
            if (user.hasAvatar) {
                UserAvatar(url = user.avatarURL) {}
            } else {
                UserAvatar {}
            }
        }
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.icon_right_arrow),
            contentDescription = user.name,
            modifier = Modifier.height(20.dp)
        )
    }

}