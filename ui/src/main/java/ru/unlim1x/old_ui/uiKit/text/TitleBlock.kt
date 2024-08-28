package ru.unlim1x.old_ui.uiKit.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.old_ui.uiKit.theme.MyTypography
import ru.unlim1x.old_ui.uiKit.theme.myColorScheme

@Composable
internal fun TitleBlock(
    heading: String, description: String, modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
) {
    Column(
        modifier = modifier.requiredWidth(200.dp),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(text = heading, style = MyTypography.subheading1)
        Text(text = description, color = myColorScheme.neutralWeak, style = MyTypography.bodyText2)
    }
}

@Preview
@Composable
fun TestTitle() {
    TitleBlock(heading = "Heading 1", description = "SF Pro Display/32/Bold")
}