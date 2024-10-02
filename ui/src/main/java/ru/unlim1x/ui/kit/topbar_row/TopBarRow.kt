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
    var wholeRowWidth by remember { mutableStateOf(360.dp) }
    var wholeRowHeight by remember { mutableStateOf(0.dp) }
    var basicSearchWidth by remember { mutableStateOf(32.dp) }
    val density = LocalDensity.current
    //var widthAnimationValue by remember{ mutableFloatStateOf(320f)}
    val animationWidth by animateFloatAsState(
        targetValue = basicSearchWidth.value,
        animationSpec = tween(durationMillis = 100),
        visibilityThreshold = 0.1f
    )
    val keyboard = LocalSoftwareKeyboardController.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            //.height(wholeRowHeight)
            .onGloballyPositioned {
                wholeRowWidth = (density.run {
                    (it.size.width)
                        .toDp()
                        .takeIf { it > 0.dp } ?: wholeRowWidth
                })
            }, //verticalAlignment = Alignment.CenterVertically,
        //horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        var isFocused by remember { mutableStateOf(false) }

            BoxWithConstraints(
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(DevMeetTheme.colorScheme.disabled)
                    .padding(10.dp)
                //.weight(0.1f)
                //.fillMaxWidth()
                ,
                contentAlignment = Alignment.CenterStart
            ) {
                if (text.isEmpty() && !isFocused) {
                    Row(
                        modifier = Modifier
                            .onGloballyPositioned {
                                wholeRowHeight= (density.run {
                                    (it.size.height)
                                        .toDp().takeIf { it>wholeRowHeight }?:wholeRowHeight
                                })
                            }, verticalAlignment = Alignment.CenterVertically,
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

//                Row(
//                    modifier = Modifier
//                        //.padding(10.dp)
//                        //.padding(end = 22.dp)
//                        .height(22.dp), verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
                    //Icon(imageVector = Icons.Filled.Search, contentDescription = "Search", tint = Color.Transparent)
                    BasicTextField(
                        modifier = Modifier
                            .height(wholeRowHeight)
                            .width(animationWidth.dp) ///HERE!!!!!
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


                //}
                if (text.isNotEmpty()) {
                    Icon(imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear",
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

        BoxWithConstraints(
            modifier = Modifier.align(Alignment.CenterEnd)
                .sizeIn(minHeight = 44.dp)
                .onGloballyPositioned {
                    basicSearchWidth =
                        ((wholeRowWidth - (density
                            .run { (2*it.size.width).toDp() }
//                            .takeIf { it > 0.dp } ?: basicSearchWidth
                            )))
//                    println("WHOLE ROW ${wholeRowWidth}")
//                    println("BOX WITH TEXT ${it.size.width}")
//                    //println("EMPTY? ${textIsEmpty}")
                    println("BOX WITH TEXT toDp ${density.run { it.size.width.toDp() }}")
                    println("BOX WITH TEXT toDp value ${density.run { it.size.width.toDp().value }}")
                }
                //.requiredWidth(32.dp)
                //.requiredWidthIn(min=32.dp, max = 128.dp)

                //.align(Alignment.CenterEnd)
                .clickable { if (textIsEmpty && !isFocused) onMenuItemClick() }

                ,
        ) {
           // LaunchedEffect(key1 = !(!textIsEmpty || isFocused)) {
                //basicSearchWidth =
                    //((wholeRowWidth - ((constraints.minWidth)/density.density).dp))
                println("MIN WIDTH ${(constraints.minWidth/density.density).dp}")
            println("MAX WIDTH ${(constraints.maxWidth/density.density).dp}")
           // }

                    //.run { constraints.minWidth.toDp() })))
                    //.takeIf { it > 0.dp } ?: basicSearchWidth)))
            AnimatedContent(targetState = !textIsEmpty || isFocused,
                modifier = Modifier
                    .align(Alignment.Center)
                    ,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(50)) +
                            scaleIn(initialScale = 0.1f, animationSpec = tween(100))
                    )
                        .togetherWith(fadeOut(animationSpec = tween(50)))
                }) {
                if (!it) {

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
println("XYZ ${wholeRowWidth.value}")
    println("XYZ ${basicSearchWidth.value}")
}

@Composable
internal fun ProfileMenuIcon(modifier: Modifier=Modifier) {
    //Box(modifier = modifier.height(44.dp).sizeIn(minWidth = 32.dp)){
        Icon(
            modifier=modifier.sizeIn(minWidth = 32.dp),
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Профиль",
            tint = DevMeetTheme.colorScheme.primary
        )
   // }
}

@Composable
private fun CancelMenuIcon(modifier: Modifier, onClick: () -> Unit) {
    Box(modifier.clickable { onClick() }){
        Text(
            modifier=modifier.sizeIn(minWidth = 32.dp),
            text = "Отмена",
            maxLines = 1,
            style = DevMeetTheme.newTypography.h4,
            color = DevMeetTheme.colorScheme.primary
        )

    }
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
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Show() {
    val focusManager = LocalFocusManager.current
    TopBarSearchRow(onSearch = {},
        onValueChanged = {},
        onMenuItemClick = {}) {

    }

}