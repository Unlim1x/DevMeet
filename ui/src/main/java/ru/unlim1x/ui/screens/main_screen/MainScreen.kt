package ru.unlim1x.ui.screens.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.ui.kit.community.CommunityCard
import ru.unlim1x.ui.kit.community.CommunityUI
import ru.unlim1x.ui.kit.event_card.EventCard
import ru.unlim1x.ui.kit.event_card.EventCardVariant
import ru.unlim1x.ui.kit.event_card.EventUI
import ru.unlim1x.ui.kit.tag.TagMedium

private const val HORIZONTAL_PADDING = 16
private const val ROW_CARD_FRACTION = 0.8f

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun MainScreen() {
    val stateForCard = EventUI(
        1, "Test name", "", "10 may",
        address = "Some address, 15", tags = listOf("testing", "devops", "some tag")
    )
    val stateForCommunity = CommunityUI(imageUri = "", name = "Хабр", id = 1, isSubscribed = false)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = HORIZONTAL_PADDING.dp)
            .padding(bottom = 24.dp)
    ) {
        var topBarPadding by remember { mutableStateOf(0) }
        val density = LocalDensity.current.density
        Text(text = "Здесь будет поисковая строка", modifier = Modifier
            .align(Alignment.TopCenter)
            .onGloballyPositioned {
                topBarPadding =
                    (it.size.height / density).toInt()
            })
        LazyColumn(
            modifier = Modifier.padding(top = topBarPadding.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(HORIZONTAL_PADDING.dp)
                ) {
                    repeat(5) {
                        item {
                            EventCard(
                                modifier = Modifier.fillParentMaxWidth(ROW_CARD_FRACTION),
                                state = stateForCard
                            )
                        }
                    }

                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "Заголовок сун встреч", style = DevMeetTheme.newTypography.h2)
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(HORIZONTAL_PADDING.dp)
                    ) {
                        repeat(8) {
                            item {
                                EventCard(
                                    state = stateForCard,
                                    variant = EventCardVariant.COMPACT
                                )
                            }
                        }

                    }
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "Заголовок первого рэила", style = DevMeetTheme.newTypography.h2)
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(HORIZONTAL_PADDING.dp)
                    ) {
                        repeat(5) {
                            item {
                                CommunityCard(
                                    state = stateForCommunity,
                                    onSubscribeClick = { /*TODO*/ }) {

                                }
                            }
                        }
                    }
                }
            }


            //КОНЕЦ ГЛАВНОГО БЛОКА
            item {
                val tagList = listOf(
                    "Дизайн",
                    "Разработка",
                    "Mobile",
                    "Frontend",
                    "Backend",
                    "DevOps",
                    "AI",
                    "Машинное Обучение",
                    "Базы Данных",
                    "Безопасность",
                    "UI/UX",
                    "Веб-Разработка",
                    "Облачные Технологии",
                    "Тестирование",
                    "Разработка Игр",
                    "Все категории"
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "Другие встречи", style = DevMeetTheme.newTypography.h2)
                    FlowRow(modifier = Modifier) {
                        tagList.forEach {
                            TagMedium(
                                text = it,
                                modifier = Modifier.padding(4.dp),
                                selected = false
                            ) {

                            }
                        }
                    }
                }
            }

            //Здесь идет бесконечный список с вставками каждые три ивента

            val list = MutableList(25) {
                it
            }
            itemsIndexed(list) { index, item ->
                EventCard(state = stateForCard)
                if (index == 2) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        modifier = Modifier.height(120.dp),
                        text = "ЭТО БУДЕТ БАННЕР",
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                }
                if (index == 5) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        modifier = Modifier.height(120.dp),
                        text = "ЭТО БУДУТ ЧЕЛОВЕЧКИ КОТОРЫХ МОЖНО ЗНАТ",
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                }
                if (index > 6 && (index + 1) % 3 == 0) {
                    //RAIL!!!!

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(
                            text = "Заголовок следующего рэила",
                            style = DevMeetTheme.newTypography.h2
                        )
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(HORIZONTAL_PADDING.dp)
                        ) {
                            repeat(5) {
                                item {
                                    CommunityCard(
                                        state = stateForCommunity,
                                        onSubscribeClick = { /*TODO*/ }) {

                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                    }

                }
            }
        }

    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Show() {
    MainScreen()
}