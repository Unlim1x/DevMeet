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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.ui.R

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
internal fun TopBarSearchRow(
    text: String = "",
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember {
        MutableInteractionSource()
    },
    focusRequester: FocusRequester = remember { FocusRequester() },
    hintColor: Color = DevMeetTheme.colorScheme.secondary,
    onSearch: (string: String) -> Unit,
    onValueChanged: (text: String) -> Unit,
    menuDischargeContent: @Composable () -> Unit = { ProfileMenuIcon() },
    onMenuItemClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var textIsEmpty by remember {
        mutableStateOf(true)
    }
    var text by remember { mutableStateOf(text) }
    var wholeRowWidth by remember { mutableIntStateOf(320) }
    var basicSearchWidth by remember { mutableIntStateOf(320) }
    val density = LocalDensity.current
    //var widthAnimationValue by remember{ mutableFloatStateOf(320f)}
    val animationWidth by animateFloatAsState(
        targetValue = basicSearchWidth.toFloat(),
        animationSpec = tween(durationMillis = 100)
    )
    val keyboard = LocalSoftwareKeyboardController.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                wholeRowWidth = (it.size.width)
            }, verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        var isFocused by remember { mutableStateOf(false) }
        Row(
            Modifier
                //.weight(1f)
                .padding(end = 8.dp)) {
            Box(
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(DevMeetTheme.colorScheme.disabled)
                    .padding(10.dp)
                //.weight(1f)
                //.fillMaxWidth()
                ,
                contentAlignment = Alignment.CenterStart
            ) {
                if (text.isEmpty() && !isFocused) {
                    Row(
                        modifier = Modifier, verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = hintColor,
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = "Найти встречи и сообщества",
                            style = DevMeetTheme.newTypography.secondary
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        //.padding(10.dp)
                        //.padding(end = 22.dp)
                        .height(22.dp), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    //Icon(imageVector = Icons.Filled.Search, contentDescription = "Search", tint = Color.Transparent)
                    BasicTextField(
                        modifier = Modifier
                            //.width(animationWidth.dp) ///HERE!!!!!
                            .focusRequester(focusRequester)

                            //.weight(1f)
                            //.fillMaxWidth()
                            .onFocusChanged {
                                isFocused = it.isFocused
                            },
                        value = text,
                        singleLine = true,
                        onValueChange = {
                            text = it
                            onValueChanged(it)
                            textIsEmpty = it.isEmpty()
                        },
                        interactionSource = interactionSource,
                        textStyle = DevMeetTheme.newTypography.secondary.copy(DevMeetTheme.colorScheme.black),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            isFocused = false
                            focusManager.clearFocus()
                            onSearch(text)
                            keyboard?.hide()
                        }),
                    )


                }
                if (text.isNotEmpty()) {
                    Icon(imageVector = Icons.Filled.Clear,
                        contentDescription = "Search",
                        tint = hintColor,
                        modifier = Modifier
                            .size(22.dp)
                            .align(Alignment.CenterEnd)
                            .clickable {
                                text = ""
                                onValueChanged("")
                            })

                }
            }
        }
        Box(
            modifier = Modifier
                .onGloballyPositioned {
                    basicSearchWidth =
                        ((wholeRowWidth - it.size.width)).toInt()
                }
                //.align(Alignment.CenterEnd)
                .clickable { if (textIsEmpty && !isFocused) onMenuItemClick() }

                .sizeIn(minWidth = 32.dp, minHeight = 44.dp),
        ) {
            AnimatedContent(targetState = textIsEmpty && !isFocused,
                modifier = Modifier.align(Alignment.Center),
                transitionSpec = {
                    (fadeIn(animationSpec = tween(50)) +
                            scaleIn(initialScale = 0.1f, animationSpec = tween(100)))
                        .togetherWith(fadeOut(animationSpec = tween(50)))
                }) {
                if (it) {

                    menuDischargeContent()
                } else {
                    CancelMenuIcon(Modifier) {
                        text = ""
                        textIsEmpty = true
                        isFocused = false
                        focusManager.clearFocus()
                        onCancelClick()
                    }
                }
            }


        }

    }


}

@Composable
internal fun ProfileMenuIcon() {
    Icon(
        modifier = Modifier.height(44.dp),
        painter = painterResource(id = R.drawable.user),
        contentDescription = "Профиль",
        tint = DevMeetTheme.colorScheme.primary
    )
}

@Composable
private fun CancelMenuIcon(modifier: Modifier, onClick: () -> Unit) {
    Text(
        modifier = modifier.clickable { onClick() },
        text = "Отмена",
        maxLines = 1,
        style = DevMeetTheme.newTypography.h4,
        color = DevMeetTheme.colorScheme.primary
    )
}

@Composable
internal fun SearchBar(
    text: String,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember {
        MutableInteractionSource()
    },
    focusRequester: FocusRequester = remember { FocusRequester() },
    hintColor: Color = DevMeetTheme.colorScheme.secondary,
    onSearch: (string: String) -> Unit,
    onValueChanged: (text: String) -> Unit
) {
    //var text by remember{mutableStateOf(text)}

    var isFocused by remember { mutableStateOf(false) }
    Row(modifier) {
        Box(
            modifier
                .clip(RoundedCornerShape(16.dp))
                .background(DevMeetTheme.colorScheme.disabled)
                .padding(10.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            if (text.isEmpty() && !isFocused) {
                Row(
                    modifier = Modifier, verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = hintColor,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "Найти встречи и сообщества",
                        style = DevMeetTheme.newTypography.secondary
                    )
                }
            }

            Row(
                modifier = Modifier
                    //.padding(10.dp)
                    //.padding(end = 22.dp)
                    .height(22.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //Icon(imageVector = Icons.Filled.Search, contentDescription = "Search", tint = Color.Transparent)
                BasicTextField(
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth()
                        .onFocusChanged {
                            isFocused = it.isFocused
                        },
                    value = text,
                    onValueChange = {//text=it
                        onValueChanged(it)
                    },
                    interactionSource = interactionSource,
                    textStyle = DevMeetTheme.newTypography.secondary.copy(DevMeetTheme.colorScheme.black),
                    keyboardActions = KeyboardActions(onSearch = {
                        onSearch(text)

                    }),
                )


            }
            if (text.isNotEmpty()) {
                Icon(imageVector = Icons.Filled.Clear,
                    contentDescription = "Search",
                    tint = hintColor,
                    modifier = Modifier
                        .size(22.dp)
                        .align(Alignment.CenterEnd)
                        .clickable {
                            // text = ""
                            onValueChanged("")
                        })

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun Show() {
    val focusManager = LocalFocusManager.current
    TopBarSearchRow(onSearch = {},
        onValueChanged = {},
        menuDischargeContent = { ProfileMenuIcon() },
        onMenuItemClick = {}) {

    }

}