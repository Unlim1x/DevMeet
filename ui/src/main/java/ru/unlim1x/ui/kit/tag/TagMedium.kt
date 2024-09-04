package ru.unlim1x.ui.kit.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.old_ui.theme.DevMeetTheme

private const val FIGMA_RADIUS = 8
private const val PADDING = 8

@Composable
internal fun TagMedium(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    val textColor = when (selected) {
        true -> Color.White
        false -> DevMeetTheme.colorScheme.primary
    }
    val backgroundColor = when (selected) {
        true -> DevMeetTheme.colorScheme.primary
        false -> DevMeetTheme.colorScheme.disabled
    }
    Box(modifier = modifier
        .background(
            color = backgroundColor, shape = RoundedCornerShape(
                FIGMA_RADIUS.dp
            )
        )
        .clip(RoundedCornerShape(FIGMA_RADIUS.dp))
        .clickable { onClick() }
        .padding(PADDING.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = DevMeetTheme.newTypography.medium, color = textColor)
    }
}

@Composable
@Preview
private fun showTagMedium() {
    TagMedium(text = "Тестирование", selected = false) {}
}