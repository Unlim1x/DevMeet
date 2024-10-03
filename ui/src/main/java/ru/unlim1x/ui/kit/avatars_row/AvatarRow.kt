package ru.unlim1x.ui.kit.avatars_row

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.ui.R

@Composable
internal fun AvatarRow(modifier: Modifier =Modifier,listAvatars: List<String>,
                       emptyContent: @Composable ()->Unit = { EmptyPeopleText() }
) {
    var zIndexCounter by rememberSaveable {
        mutableIntStateOf(listAvatars.size+1)
    }
    var notShown by rememberSaveable {
        mutableIntStateOf(listAvatars.size - 5)
    }
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy((-14).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (listAvatars.size) {
            in 1..8 -> items(listAvatars) {
                ZIndexAvatar(zIndex = -zIndexCounter--.toFloat(), url = it)
            }

            in 8..Int.MAX_VALUE -> {
                items(listAvatars.subList(0, 8)) {
                    ZIndexAvatar(zIndex = -zIndexCounter--.toFloat(), url = it)
                }
                item {
                    notShown = listAvatars.size - 8
                    ZIndexNumber(zIndex = -zIndexCounter.toFloat(), notShown = notShown)
                }
            }

            else -> {
                //item{emptyContent}
            }
        }
    }
    if(listAvatars.isEmpty()){
        emptyContent()
    }

}


@Composable
private fun ZIndexAvatar(zIndex: Float, url: String) {
    Box(
        modifier = Modifier
            .zIndex(zIndex)
            .size(48.dp)
            //.clip(CircleShape)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            contentDescription = "User avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxSize()
                .border(
                    color = DevMeetTheme.colorScheme.neutralWeak,
                    width = 2.dp,
                    shape = RoundedCornerShape(10.dp)
                ),
            placeholder = painterResource(id = R.drawable.community_avatar),
            error = painterResource(id = R.drawable.community_avatar)
        )
    }
}

@Composable
private fun ZIndexNumber(zIndex: Float, notShown: Int) {
    Box(
        modifier = Modifier
            .zIndex(zIndex)
            .size(48.dp)
            .clip(CircleShape)
            .background(DevMeetTheme.colorScheme.disabled)
            .border(width = 2.dp, color = Color.White, shape = CircleShape)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "+ $notShown",
            style = DevMeetTheme.newTypography.secondary,
            color = DevMeetTheme.colorScheme.primary
        )
    }
}

@Composable
private fun EmptyPeopleText(){
    Text(text = "Будьте первым!", style = DevMeetTheme.newTypography.big)
}


@Preview
@Composable
private fun showAvatarLine() {

    //AvatarRow(listAvatars = listOf("", "", "", "", "", "", "", "", "", "", "", "", ""))
    AvatarRow(listAvatars = emptyList())
}