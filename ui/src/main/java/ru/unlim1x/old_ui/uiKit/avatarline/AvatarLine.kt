package ru.unlim1x.old_ui.uiKit.avatarline

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
internal fun AvatarLine(listAvatars: List<String>) {
    var zIndexCounter by rememberSaveable {
        mutableIntStateOf(listAvatars.size)
    }
    var notShown by rememberSaveable {
        mutableIntStateOf(listAvatars.size - 5)
    }
    LazyRow(
        modifier = Modifier.requiredHeight(48.dp),
        horizontalArrangement = Arrangement.spacedBy((-14).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (listAvatars.size) {
            in 1..5 -> items(listAvatars) {
                ZIndexElement(zIndex = zIndexCounter--.toFloat(), url = it)
            }

            in 5..Int.MAX_VALUE -> {
                items(listAvatars.subList(0, 5)) {
                    ZIndexElement(zIndex = zIndexCounter--.toFloat(), url = it)
                }
                item {
                    notShown = listAvatars.size - 5
                    Text(
                        modifier = Modifier.padding(28.dp, 0.dp, 0.dp, 0.dp),
                        text = "+ $notShown",
                        style = DevMeetTheme.typography.bodyText1
                    )
                }
            }

            else -> {

            }
        }
    }

}


@Composable
private fun ZIndexElement(zIndex: Float, url: String) {
    Box(
        modifier = Modifier
            .zIndex(zIndex)
            .size(48.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            contentDescription = "User avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
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


@Preview
@Composable
private fun showAvatarLine() {
    AvatarLine(listOf("", "", "", "", "", "", ""))

}