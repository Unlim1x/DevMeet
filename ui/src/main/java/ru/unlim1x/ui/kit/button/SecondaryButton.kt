package ru.unlim1x.ui.kit.button

import android.view.SoundEffectConstants
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.ui.R
import ru.unlim1x.ui.kit.brush.secondaryLinearBrush

private const val FIGMA_RADIUS = 16
private const val FIGMA_HEIGHT = 56

@Composable
internal fun SecondaryButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val view = LocalView.current
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(FIGMA_RADIUS.dp))
            .fillMaxWidth()
            .defaultMinSize(minHeight = FIGMA_HEIGHT.dp)
            .background(secondaryLinearBrush())
            .clickable {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = text),
            style = DevMeetTheme.newTypography.h3,
            color = DevMeetTheme.colorScheme.primary
        )
    }
}

@Composable
@Preview
private fun showButton() {
    SecondaryButton(R.string.pay) {}
}