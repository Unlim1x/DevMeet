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
import ru.unlim1x.wb_project.ui.theme.Wb_projectTheme

@Composable
fun Chip(text: String, modifier: Modifier = Modifier) {
    Box(

        modifier = modifier
            .height(20.dp)
            .background(
                Wb_projectTheme.colorScheme.brandBackground,
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
            style = Wb_projectTheme.typography.metadata3,
            color = Wb_projectTheme.colorScheme.brandDark
        )
    }
}

@Preview
@Composable
fun ShowChip() {
    Chip(text = "Default")
}