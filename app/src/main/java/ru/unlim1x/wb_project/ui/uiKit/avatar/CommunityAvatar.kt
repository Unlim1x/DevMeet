package ru.unlim1x.wb_project.ui.uiKit.avatar


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.unlim1x.wb_project.R

@Composable
fun CommunityAvatar(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.community_avatar),
    contentDescription: String = "Community avatar"
) {
    MeetingAvatar(modifier = modifier, painter = painter, contentDescription = contentDescription)
}

@Preview
@Composable
fun ShowCommunityAvatar() {
    CommunityAvatar()
}