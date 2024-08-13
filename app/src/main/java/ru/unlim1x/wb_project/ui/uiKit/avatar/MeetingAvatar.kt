package ru.unlim1x.wb_project.ui.uiKit.avatar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.wb_project.R

@Composable
internal fun MeetingAvatar(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.event_avatar),
    contentDescription: String = "Meeting avatar"
) {
    Box(modifier = modifier.size(50.dp)) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = contentDescription
        )
    }
}

@Preview
@Composable
private fun ShowMeetingAvatar() {
    MeetingAvatar()
}