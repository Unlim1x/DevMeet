package ru.unlim1x.ui.kit.banner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.ui.R
import ru.unlim1x.ui.kit.brush.primaryLinearBrush
import ru.unlim1x.ui.kit.button.SecondaryButton

@Composable
internal fun Banner(modifier: Modifier = Modifier, onButtonClick: () -> Unit) {
    var textWidth by remember {
        mutableIntStateOf(220)
    }
    val density = LocalDensity.current.density
    Box(modifier = Modifier
        .then(modifier)
        .clip(RoundedCornerShape(16.dp))
        .fillMaxWidth()
        .height(120.dp)
        .background(primaryLinearBrush())
        .onGloballyPositioned {
            textWidth = (it.size.width / 3).toInt()
        }
    ) {


        Row(Modifier.align(Alignment.CenterEnd)) {
            Image(
                painterResource(id = R.drawable.arrow_left), contentDescription = "",
                modifier = Modifier.offset(x = 20.dp)
            )
            Image(painterResource(id = R.drawable.arrow_right), contentDescription = "")
        }
        Column(Modifier.width(textWidth.dp)) {
            Text(
                text = "Расскажите о своих интересах, чтобы мы рекомендовали полезные встречи",
                style = DevMeetTheme.newTypography.secondary, color = Color.White,
                minLines = 3, fontSize = 14.sp, lineHeight = 16.sp,
                modifier = Modifier
                    .width(250.dp)
                    .padding(start = 8.dp, top = 8.dp)
            )
            SecondaryButton(
                text = R.string.choose_interests_button, modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    ),
                fontSize = 16.sp
            ) {
                onButtonClick()
            }
        }
    }

}

@Preview()
@Composable
private fun Show() {
    Banner() {}

}