package ru.unlim1x.ui.kit.topbar_row

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.ui.R

@Composable
internal fun TopBarSearchRow(
    modifier: Modifier = Modifier,
    textString: String = "",
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
    hintColor: Color = DevMeetTheme.colorScheme.secondary,
    onSearch: (string: String) -> Unit,
    onValueChanged: (text: String) -> Unit,
    menuDischargeContent: @Composable () -> Unit = { ProfileMenuIcon() },
    onMenuItemClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var textIsEmpty by remember { mutableStateOf(true) }
    var text by remember { mutableStateOf(textString) }
    var wholeRowWidth by remember { mutableStateOf(360.dp) }
    var basicSearchWidth by remember { mutableStateOf(32.dp) }
    val density = LocalDensity.current
    val animationWidth by animateFloatAsState(
        targetValue = basicSearchWidth.value,
        animationSpec = tween(durationMillis = 100),
        visibilityThreshold = 0.1f, label = "AnimationWidth"
    )
    val keyboard = LocalSoftwareKeyboardController.current
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                wholeRowWidth = density.run {
                    (it.size.width)
                        .toDp()
                        .takeIf { it > 0.dp } ?: wholeRowWidth
                }
            }
    ) {
        SearchBoxContent(
            text = text,
            isFocused = isFocused,
            hintColor = hintColor,
            onValueChanged = { newText ->
                text = newText
                onValueChanged(newText)
                textIsEmpty = newText.isEmpty()
            },
            onFocusChanged = { focusState -> isFocused = focusState },
            focusRequester = focusRequester,
            animationWidth = animationWidth,
            interactionSource = interactionSource,
            onSearch = {
                focusManager.clearFocus()
                onSearch(text)
                keyboard?.hide()
            },
            onClearText = { text = ""; onValueChanged("") }
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .sizeIn(minHeight = 44.dp)
                .onGloballyPositioned {
                    basicSearchWidth =
                        ((wholeRowWidth - density.run { (2 * it.size.width).toDp() }))
                }
                .clickable { if (textIsEmpty && !isFocused) onMenuItemClick() }
        ) {
            AnimatedMenuContent(
                modifier= Modifier.align(Alignment.Center),
                textIsEmpty = textIsEmpty,
                isFocused = isFocused,
                menuDischargeContent = menuDischargeContent,
                onCancelClick = {
                    text = ""
                    textIsEmpty = true
                    isFocused = false
                    focusManager.clearFocus()
                    onCancelClick()
                }
            )
        }
    }
}

@Composable
private fun SearchBoxContent(
    text: String,
    isFocused: Boolean,
    hintColor: Color,
    onValueChanged: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    focusRequester: FocusRequester,
    animationWidth: Float,
    interactionSource: MutableInteractionSource,
    onSearch: () -> Unit,
    onClearText: () -> Unit
) {
    val density = LocalDensity.current
    val wholeRowHeight = remember { mutableStateOf(0.dp) }
    BoxWithConstraints(
        Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(DevMeetTheme.colorScheme.disabled)
            .padding(10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (text.isEmpty() && !isFocused) {
            Row(
                modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                    wholeRowHeight.value = with(density) {
                        layoutCoordinates.size.height.toDp()
                    }
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search),
                    tint = hintColor,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = stringResource(R.string.find_meetings_and_communities),
                    style = DevMeetTheme.newTypography.secondary
                )
            }
        }

        BasicTextField(
            modifier = Modifier
                .height(wholeRowHeight.value)
                .width(animationWidth.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { onFocusChanged(it.isFocused) },
            value = text,
            singleLine = true,
            onValueChange = onValueChanged,
            interactionSource = interactionSource,
            textStyle = DevMeetTheme.newTypography.secondary.copy(DevMeetTheme.colorScheme.black),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch() })
        )

        if (text.isNotEmpty()) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = stringResource(R.string.clear_text),
                tint = hintColor,
                modifier = Modifier
                    .size(22.dp)
                    .align(Alignment.CenterEnd)
                    .clickable { onClearText() }
            )
        }
    }
}

@Composable
private fun AnimatedMenuContent(
    modifier: Modifier = Modifier,
    textIsEmpty: Boolean,
    isFocused: Boolean,
    menuDischargeContent: @Composable () -> Unit,
    onCancelClick: () -> Unit
) {
    AnimatedContent(
        targetState = !textIsEmpty || isFocused,
        modifier = modifier,
        transitionSpec = {
            fadeIn(animationSpec = tween(50)) +
                    scaleIn(initialScale = 0.1f, animationSpec = tween(100)) togetherWith
                    fadeOut(animationSpec = tween(50))
        }, label = "MenuChangeAnimation"
    ) {
        if (!it) {
            menuDischargeContent()
        } else {
            CancelMenuIcon(onCancelClick)
        }
    }
}

@Composable
internal fun ProfileMenuIcon(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier.sizeIn(minWidth = 32.dp),
        painter = painterResource(id = R.drawable.user),
        contentDescription = "Профиль",
        tint = DevMeetTheme.colorScheme.primary
    )
}

@Composable
private fun CancelMenuIcon(onClick: () -> Unit) {
    Box(Modifier.clickable { onClick() }) {
        Text(
            modifier = Modifier.sizeIn(minWidth = 32.dp),
            text = stringResource(R.string.cancel),
            maxLines = 1,
            style = DevMeetTheme.newTypography.h4,
            color = DevMeetTheme.colorScheme.primary
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Show() {
    TopBarSearchRow(onSearch = {},
        onValueChanged = {},
        onMenuItemClick = {}) {

    }

}