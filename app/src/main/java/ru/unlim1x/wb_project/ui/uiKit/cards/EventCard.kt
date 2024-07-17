package ru.unlim1x.wb_project.ui.uiKit.cards

import android.view.SoundEffectConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.avatar.MeetingAvatar
import ru.unlim1x.wb_project.ui.uiKit.chips.Chip

@Composable
fun EventCard(
    heading: String, timeAndPlace: String, isOver: Boolean = false, tags: List<String>? = null,
    onClick: () -> Unit
) {
    val view = LocalView.current
    Column(modifier = Modifier
        .clickable {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            onClick()
        }
        .background(DevMeetTheme.colorScheme.neutralWhite)
        .padding(4.dp)
        .fillMaxWidth()
    ) {

        Row(verticalAlignment = Alignment.Top) {

            MeetingAvatar(modifier = Modifier.padding(vertical = 4.dp))
            CardBody(heading = heading, meta = timeAndPlace, meta2Flag = isOver, tags = tags)

        }
        Spacer(modifier = Modifier.height(if (tags != null) 20.dp else 4.dp))
        HorizontalDivider()
    }
}

@Composable
fun CardBody(
    heading: String,
    meta: String,
    meta2: String = stringResource(id = R.string.is_over),
    meta2Flag: Boolean = false,
    tags: List<String>?
) {
    Column(modifier = Modifier.padding(start = 12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = heading, style = DevMeetTheme.typography.bodyText1)
            Text(
                modifier = Modifier.alpha(if (!meta2Flag) 0f else 1f),
                text = meta2,
                style = DevMeetTheme.typography.metadata2
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = meta, style = DevMeetTheme.typography.metadata1)
        Spacer(modifier = Modifier.height(2.dp))
        LazyRow {
            tags?.let { list ->
                items(list) { tag ->
                    Chip(text = tag, modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp))
                }
            }
        }


    }
}

@Composable
@Preview
fun ShowEventCard() {

    val timePlace = TimeAndPlace()
    val tags = listOf("Junior", "Python", "Moscow")
    EventCard(
        heading = "Developer meeting",
        timeAndPlace = timePlace.dateAndPlaceString,
        tags = tags
    ) {}
}