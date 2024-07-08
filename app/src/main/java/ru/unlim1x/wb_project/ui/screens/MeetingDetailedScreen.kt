package ru.unlim1x.wb_project.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.theme.Wb_projectTheme
import ru.unlim1x.wb_project.ui.uiKit.avatarline.AvatarLine
import ru.unlim1x.wb_project.ui.uiKit.buttons.PrimaryButton
import ru.unlim1x.wb_project.ui.uiKit.buttons.SecondaryButton
import ru.unlim1x.wb_project.ui.uiKit.cards.TimeAndPlace
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Event

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingDetailedScreen(navController: NavController, eventName: String, eventId: Int) {

    val FIGMA_HORIZONTAL_PADDING = 16.dp
    val FIGMA_GAP = 16.dp

    val meGo = remember { mutableStateOf(false) }
    var topBarAnimation by remember { mutableStateOf(false) }

    Scaffold(containerColor = Wb_projectTheme.colorScheme.neutralWhite,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Wb_projectTheme.colorScheme.neutralWhite,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        eventName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = Wb_projectTheme.typography.subheading1
                    )
                },
                navigationIcon = {
                    AnimatedVisibility(
                        visible = topBarAnimation,
                        enter = slideInHorizontally(
                            animationSpec = tween(durationMillis = 500),
                            initialOffsetX = { -it })
                    ) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Назад"
                            )
                        }
                    }

                },
                actions = {
                    if (meGo.value) {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Иду",
                                tint = Wb_projectTheme.colorScheme.brandDefault
                            )
                        }
                    }

                }


            )
        }) {

        topBarAnimation = true
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

        LazyColumn(modifier = modifier.padding(horizontal = FIGMA_HORIZONTAL_PADDING)) {

            item { Spacer(modifier = Modifier.size(FIGMA_GAP)) }

            item {
                Text(
                    text = event.timeAndPlace.dateAndPlaceString,
                    style = Wb_projectTheme.typography.bodyText1,
                    color = Wb_projectTheme.colorScheme.neutralWeak
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(175.dp)
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

            item { Text(text = event.description, style = Wb_projectTheme.typography.metadata1) }

            item { Spacer(modifier = Modifier.size(FIGMA_GAP)) }

            item { AvatarLine(listAvatars = listOfAvatars) }

            item { Spacer(modifier = Modifier.size(FIGMA_GAP)) }

            when (meGo.value) {
                true -> {
                    item {
                        SecondaryButton(
                            modifier = Modifier.fillMaxWidth(),
                            buttonText = "Схожу в другой раз"
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
                            buttonText = "Пойду на встречу!"
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
@Preview
fun ShowEventDetailed() {
    MeetingDetailedScreen(
        navController = rememberNavController(),
        eventName = "Some event",
        eventId = 1
    )
}