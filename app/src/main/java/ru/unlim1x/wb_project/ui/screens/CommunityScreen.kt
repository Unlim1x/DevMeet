package ru.unlim1x.wb_project.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.unlim1x.wb_project.ui.theme.Wb_projectTheme

@Composable
fun CommunityScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Скоро...", style = Wb_projectTheme.typography.heading1)
    }
}