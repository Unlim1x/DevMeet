package ru.unlim1x.old_ui.screens.meeting_detailed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import org.koin.androidx.compose.koinViewModel
import ru.lim1x.domain.models.MeetingDetailedExt
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.old_ui.uiKit.avatarline.AvatarLine
import ru.unlim1x.old_ui.uiKit.buttons.PrimaryButton
import ru.unlim1x.old_ui.uiKit.buttons.SecondaryButton
import ru.unlim1x.old_ui.uiKit.topbar.AppBarMenuItems
import ru.unlim1x.old_ui.uiKit.topbar.TopBar
import ru.unlim1x.ui.R

private val FIGMA_HORIZONTAL_PADDING = 16.dp
private val FIGMA_GAP = 16.dp

@Deprecated(
    message = "This composable function is deprecated since new design was announced." +
            "Be careful, in next patches it will be removed.", level = DeprecationLevel.WARNING
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MeetingDetailedScreen(
    navController: NavController,
    eventId: Int,
    viewModel: MeetingDetailedScreenViewModel = koinViewModel()
) {

    val viewState = viewModel.viewState().collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    when (val state = viewState.value) {

        is MeetingDetailedScreenViewState.Display -> {
            state.meeting.collectAsState(state.initial).value?.let {
                MeetingDetailedBody(
                    navController = navController,
                    meeting = it,
                    listOfAvatars = state.listAvatars.collectAsState(initial = emptyList()).value,
                    meGo = state.go,
                    lazyListState = lazyListState
                ) {
                    if (state.go) {
                        viewModel.obtain(MeetingDetailedScreenEvent.WillNotGo(eventId))
                    } else {
                        viewModel.obtain(MeetingDetailedScreenEvent.WillGo(eventId))
                    }
                }
            }
        }

        MeetingDetailedScreenViewState.Error -> {

        }

        MeetingDetailedScreenViewState.Loading -> {

        }
        else ->{}

    }

    LaunchedEffect(key1 = Unit) {
        viewModel.obtain(MeetingDetailedScreenEvent.LoadScreen(meetingId = eventId))
    }


}

@Composable
private fun MeetingDetailedBody(
    navController: NavController,
    lazyListState: LazyListState,
    meeting: MeetingDetailedExt,
    listOfAvatars: List<String>,
    meGo: Boolean,
    onButtonClick: () -> Unit
) {

    var showDialog by remember {
        mutableStateOf(false)
    }
    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(
                header = meeting.name,
                backIconIsVisible = true,
                backIconAction = { navController.navigateUp() },
                actionMenuItem = if (meGo) AppBarMenuItems.GoCheck else null
            )

        }) {

        val modifier = Modifier.padding(top = it.calculateTopPadding())


        if (showDialog)
            ShowImageFullScreen {
                showDialog = false
            }

        LazyColumn(
            modifier = modifier.padding(horizontal = FIGMA_HORIZONTAL_PADDING),
            state = lazyListState
        ) {

            item { Spacer(modifier = Modifier.size(FIGMA_GAP)) }

            item {
                Text(
                    text = meeting.timeAndPlace.dateAndPlaceString,
                    style = DevMeetTheme.typography.bodyText1,
                    color = DevMeetTheme.colorScheme.neutralWeak
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(175.dp)
                        .clickable { showDialog = true }
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.map_sample_2)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.map_sample_2),
                        contentDescription = "Map picture will be replaced in future",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))

                    )
                }

            }

            item { Spacer(modifier = Modifier.size(FIGMA_GAP)) }

            item { Text(text = meeting.description, style = DevMeetTheme.typography.metadata1) }

            item { Spacer(modifier = Modifier.size(FIGMA_GAP)) }

            item { AvatarLine(listAvatars = listOfAvatars) }

            item { Spacer(modifier = Modifier.size(FIGMA_GAP)) }

            when (meGo) {
                true -> {
                    item {
                        SecondaryButton(
                            modifier = Modifier.fillMaxWidth(),
                            buttonText = stringResource(R.string.i_will_come_another_time)
                        ) {
                            onButtonClick()
                        }
                    }
                }

                false -> {
                    item {
                        PrimaryButton(
                            modifier = Modifier.fillMaxWidth(),
                            buttonText = stringResource(R.string.i_will_come_to_meeting)
                        ) {
                            onButtonClick()
                        }
                    }

                }
            }


        }


    }
}

@Composable
private fun ShowImageFullScreen(dismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = {
            dismissRequest()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.map_sample_2)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.None,
            placeholder = painterResource(id = R.drawable.map_sample_2),
            contentDescription = "Map picture will be replaced in future",
            modifier = Modifier
                .height((LocalConfiguration.current.screenHeightDp / (1.5)).dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .zoomable(rememberZoomState())

        )
    }
}

@Composable
@Preview
private fun ShowEventDetailed() {
    MeetingDetailedScreen(
        navController = rememberNavController(),
        eventId = 1
    )
}