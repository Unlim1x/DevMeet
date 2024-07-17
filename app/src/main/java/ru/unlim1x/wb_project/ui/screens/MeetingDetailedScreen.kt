package ru.unlim1x.wb_project.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import ru.unlim1x.wb_project.AppBarMenuItems
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.avatarline.AvatarLine
import ru.unlim1x.wb_project.ui.uiKit.buttons.PrimaryButton
import ru.unlim1x.wb_project.ui.uiKit.buttons.SecondaryButton
import ru.unlim1x.wb_project.ui.uiKit.cards.TimeAndPlace
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Event


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingDetailedScreen(navController: NavController, eventName: String, eventId: Int) {

    val FIGMA_HORIZONTAL_PADDING = 16.dp
    val FIGMA_GAP = 16.dp

    val meGo = remember { mutableStateOf(false) }

    var showDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(header = eventName,
                backIconIsVisible = true,
                backIconAction = {navController.navigateUp()},
                actionMenuItem = if (meGo.value) AppBarMenuItems.GoCheck else null)

        }) {

        val modifier = Modifier.padding(top = it.calculateTopPadding())
        val listOfAvatars: MutableList<String> =
            MutableList(10) { "https://get.wallhere.com/photo/face-women-model-portrait-long-hair-photography-hair-nose-solo-Person-skin-head-supermodel-girl-beauty-eye-lip-blond-hairstyle-portrait-photography-photo-shoot-brown-hair-art-model-human-hair-color-hair-coloring-human-body-organ-close-up-layered-hair-5168.jpg" }
        val listOfTags = listOf("Junior", "Python", "Moscow")
        val event = Event(
            name = "Developer meeting",
            timeAndPlace = TimeAndPlace(
                place = "Moscow",
                date = 13,
                month = 9,
                year = 2024
            ),
            isFinished = false,
            tags = listOfTags
        )

        if (showDialog)
            ShowImageFullScreen {
                showDialog = false
            }

        LazyColumn(modifier = modifier.padding(horizontal = FIGMA_HORIZONTAL_PADDING)) {

            item { Spacer(modifier = Modifier.size(FIGMA_GAP)) }

            item {
                Text(
                    text = event.timeAndPlace.dateAndPlaceString,
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

            item { Text(text = event.description, style = DevMeetTheme.typography.metadata1) }

            item { Spacer(modifier = Modifier.size(FIGMA_GAP)) }

            item { AvatarLine(listAvatars = listOfAvatars) }

            item { Spacer(modifier = Modifier.size(FIGMA_GAP)) }

            when (meGo.value) {
                true -> {
                    item {
                        SecondaryButton(
                            modifier = Modifier.fillMaxWidth(),
                            buttonText = stringResource(R.string.i_will_come_another_time)
                        ) {
                            listOfAvatars.removeLast()
                            meGo.value = false
                        }
                    }
                }

                false -> {
                    item {
                        PrimaryButton(
                            modifier = Modifier.fillMaxWidth(),
                            buttonText = stringResource(R.string.i_will_come_to_meeting)
                        ) {
                            listOfAvatars.add("https://10wallpaper.com/wallpaper/1280x1024/2012/Ann_Sophie_2020_Fashion_Model_Celebrity_Photo_1280x1024.jpg")
                            meGo.value = true
                        }
                    }

                }
            }


        }


    }
}

@Composable
fun ShowImageFullScreen(dismissRequest: () -> Unit) {
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
fun ShowEventDetailed() {
    MeetingDetailedScreen(
        navController = rememberNavController(),
        eventName = "Some event",
        eventId = 1
    )
}