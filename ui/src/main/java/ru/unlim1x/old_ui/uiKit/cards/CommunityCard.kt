package ru.unlim1x.old_ui.uiKit.cards

import android.view.SoundEffectConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.old_ui.uiKit.avatar.CommunityAvatar

@Composable
internal fun CommunityCard(
    heading: String, quantity: String, tags: List<String>? = null,
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

            CommunityAvatar(modifier = Modifier.padding(vertical = 4.dp))
            CardBody(heading = heading, meta = quantity, meta2Flag = false, tags = tags)

        }
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider()
    }
}

@Preview
@Composable
private fun ShowCommunityCard() {
    val number = 53
    CommunityCard(
        heading = "Designa",
        quantity = QuantityMembers(number.toLong(), LocalContext.current).quantityString
    ) {

    }
}