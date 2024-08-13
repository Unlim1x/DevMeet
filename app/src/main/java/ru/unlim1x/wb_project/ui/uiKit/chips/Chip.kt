package ru.unlim1x.wb_project.ui.uiKit.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme

@Composable
internal fun Chip(text: String, modifier: Modifier = Modifier) {
    Box(

        modifier = modifier
            .height(20.dp)
            .background(
                DevMeetTheme.colorScheme.brandBackground,
                shape = RoundedCornerShape(40.dp)
            )
            .padding(
                PaddingValues(
                    horizontal = 8.dp, vertical = 2.dp
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = DevMeetTheme.typography.metadata3,
            color = DevMeetTheme.colorScheme.brandDark
        )
    }
}

@Preview
@Composable
private fun ShowChip() {
    Chip(text = "Default")
}