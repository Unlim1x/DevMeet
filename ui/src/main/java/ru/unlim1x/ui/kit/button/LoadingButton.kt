package ru.unlim1x.ui.kit.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.ui.kit.brush.primaryLinearBrush

private const val FIGMA_RADIUS = 16
private const val FIGMA_HEIGHT = 56
private const val PROGRESS_STROKE_WIDTH = 2

@Composable
internal fun LoadingButton(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(FIGMA_RADIUS.dp))
            .fillMaxWidth()
            .defaultMinSize(minHeight = FIGMA_HEIGHT.dp)
            .background(primaryLinearBrush())
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(20.dp),
            color = DevMeetTheme.colorScheme.neutralWhite,
            strokeWidth = PROGRESS_STROKE_WIDTH.dp
        )
    }
}

@Composable
@Preview
private fun showButton() {
    LoadingButton()
}