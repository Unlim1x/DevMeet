package ru.unlim1x.ui.kit.person

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.old_ui.uiKit.cards.loading_cards.AnimatedTransitionRoundRectangle
import ru.unlim1x.ui.kit.tag.TagSmall
import ru.unlim1x.ui.models.PersonUi


@Composable
internal fun Person(modifier: Modifier = Modifier, personUi: PersonUi, onClick: () -> Unit) {
    Box(modifier = modifier.clickable {
        onClick()
    }) {
        Column(modifier = modifier) {
            SubcomposeAsyncImage(model = personUi.imageUri, contentDescription = "",
                modifier
                    .padding(bottom = 4.dp)
                    .clip(CircleShape)
                    .requiredSize(104.dp),
                error = {
                    AnimatedTransitionRoundRectangle(
                        modifier = modifier.size(104.dp)
                    )

                })

            Text(
                text = personUi.name,
                style = DevMeetTheme.newTypography.primary,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            TagSmall(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .width(104.dp),
                text = personUi.mainTag
            ) {

            }


        }
    }
}

@Preview
@Composable
private fun Show() {
    Person(personUi = PersonUi(1, "Маша", "Тестирование", "")) {

    }

}