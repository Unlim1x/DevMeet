package ru.unlim1x.wb_project.ui.uiKit.searchfield

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.wb_project.ui.theme.Wb_projectTheme
import ru.unlim1x.wb_project.ui.uiKit.theme.myColorScheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember {
        MutableInteractionSource()
    },
    onSearch: (state: TextFieldState) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val hintColor by rememberUpdatedState(if (state.text.isEmpty()) Wb_projectTheme.colorScheme.neutralDisabled else Color.Transparent)
    val iconTint by rememberUpdatedState(if (state.text.isEmpty()) Wb_projectTheme.colorScheme.neutralDisabled else Wb_projectTheme.colorScheme.neutralActive)

    Row(
        modifier = modifier
            .hoverable(interactionSource = interactionSource)
            .fillMaxWidth()
            .background(
                myColorScheme.neutralSecondaryBackground,
                RoundedCornerShape(4.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(Icons.Filled.Search, "Search Icon", tint = iconTint)
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f, true),
        ) {
            Text(
                text = "Поиск",
                style = Wb_projectTheme.typography.bodyText1,
                color = hintColor,
            )


            BasicTextField2(
                enabled = enabled,
                modifier = Modifier.fillMaxWidth(),
                keyboardActions = KeyboardActions(onSearch = {
                    onSearch(state)
                    focusManager.clearFocus()
                }),
                interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                lineLimits = TextFieldLineLimits.SingleLine,
                textStyle = Wb_projectTheme.typography.bodyText1.copy(color = Wb_projectTheme.colorScheme.neutralActive),
                state = state,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun SerachLinePreview() {
    SearchField(rememberTextFieldState()) {}
}
