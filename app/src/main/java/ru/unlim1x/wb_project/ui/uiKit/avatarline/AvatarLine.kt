package ru.unlim1x.wb_project.ui.uiKit.avatarline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.unlim1x.wb_project.ui.theme.Wb_projectTheme
import ru.unlim1x.wb_project.ui.uiKit.avatar.MeetingAvatar

@Composable
fun AvatarLine(listAvatars: List<String>) {
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
        if (listAvatars.size <= 5)
            items(listAvatars) {
                ZIndexElement(zIndex = zIndexCounter--.toFloat())
            }
        else {
            items(listAvatars.subList(0, 5)) {
                ZIndexElement(zIndex = zIndexCounter--.toFloat())
            }
            item {
                notShown = listAvatars.size - 5
                Text(
                    modifier = Modifier.padding(28.dp, 0.dp, 0.dp, 0.dp),
                    text = "+ $notShown",
                    style = Wb_projectTheme.typography.bodyText1
                )
            }
        }

    }

}


@Composable
fun ZIndexElement(zIndex: Float) {
    Box(
        modifier = Modifier.zIndex(zIndex)
    ) {
        MeetingAvatar()
    }
}


@Preview
@Composable
fun showAvatarLine() {
    AvatarLine(listOf("", "", "", "", "", "", ""))

}