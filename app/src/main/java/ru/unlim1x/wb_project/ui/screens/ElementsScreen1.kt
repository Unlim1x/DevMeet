package ru.unlim1x.wb_project.ui.screens

import android.view.SoundEffectConstants
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.avatar.MeetingAvatar
import ru.unlim1x.wb_project.ui.uiKit.avatar.UserAvatar
import ru.unlim1x.wb_project.ui.uiKit.avatar.state.UserAvatarState
import ru.unlim1x.wb_project.ui.uiKit.avatarline.AvatarLine
import ru.unlim1x.wb_project.ui.uiKit.buttons.GhostButton
import ru.unlim1x.wb_project.ui.uiKit.buttons.PrimaryButton
import ru.unlim1x.wb_project.ui.uiKit.buttons.SecondaryButton
import ru.unlim1x.wb_project.ui.uiKit.cards.CommunityCard
import ru.unlim1x.wb_project.ui.uiKit.cards.EventCard
import ru.unlim1x.wb_project.ui.uiKit.cards.QuantityMembers
import ru.unlim1x.wb_project.ui.uiKit.cards.TimeAndPlace
import ru.unlim1x.wb_project.ui.uiKit.chips.Chip
import ru.unlim1x.wb_project.ui.uiKit.custominputview.PassCodeInput
import ru.unlim1x.wb_project.ui.uiKit.custominputview.PhoneInput
import ru.unlim1x.wb_project.ui.uiKit.custominputview.printToLog
import ru.unlim1x.wb_project.ui.uiKit.searchfield.SearchField
import ru.unlim1x.wb_project.ui.uiKit.text.TitleBlock
import ru.unlim1x.wb_project.ui.uiKit.theme.MyTypography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ElementsScreen1() {

    val context = LocalContext.current
    val navController = rememberNavController()
Column {
    TopBar(backIconIsVisible = true, backIconAction = { navController.navigateUp()})
    LazyColumn(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item { ButtonsGroup() }
        item { TypographyGroup() }
        item { UserAvatarGroup() }
        item { MeetingAvatar(modifier = Modifier.padding(4.dp)) }

        item { SearchField(state = rememberTextFieldState()) {} }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .background(DevMeetTheme.colorScheme.neutralSecondaryBackground),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Chip("Python", modifier = Modifier.padding(vertical = 4.dp))
                Chip("Junior", modifier = Modifier.padding(vertical = 4.dp))
                Chip("Moscow", modifier = Modifier.padding(vertical = 4.dp))
            }
        }
        item { EventsGroup() }

        item { CommunityGroup(1) }
        item { CommunityGroup(100000) }

        item { AvatarRowGroup() }

        item { Spacer(modifier = Modifier.size(8.dp)) }

        item {
            PhoneInput { countryCode,phone ->
                printToLog(phone)
                Toast.makeText(
                    context,
                    countryCode+phone,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        item {
            PassCodeInput { pin ->
                printToLog(pin)
                Toast.makeText(
                    context,
                    pin,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }
}



}

@Composable
fun AvatarRowGroup() {
    val list: MutableList<String> = remember {
        mutableStateListOf()
    }
    AvatarLine(listAvatars = list)
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        PrimaryButton(buttonText = "Не пойду") {
            if (list.isNotEmpty())
                list.removeLast()
        }
        PrimaryButton(buttonText = "Пойду") {
            list.add("")
        }
    }
}

@Composable
fun EventsGroup() {
    val timeAndPlace = TimeAndPlace(place = "Москва", date = 13, month = 9, year = 2024)
    val listOfTags = listOf("Junior", "Python", "Moscow")
    EventCard(
        heading = "Developer Meeting",
        timeAndPlace = timeAndPlace.dateAndPlaceString,
        tags = listOfTags
    ) {}
    EventCard(
        heading = "Developer Meeting",
        timeAndPlace = timeAndPlace.dateAndPlaceString,
        tags = listOfTags,
        isOver = true
    ) {}
    EventCard(
        heading = "Developer Meeting",
        timeAndPlace = timeAndPlace.dateAndPlaceString,
        tags = listOfTags
    ) {}
}

@Composable
fun CommunityGroup(initValue: Long) {
    var sliderPosition by remember { mutableFloatStateOf(initValue.toFloat()) }
    var number by rememberSaveable {
        mutableIntStateOf(initValue.toInt())
    }

    CommunityCard(
        heading = "Designa",
        quantity = QuantityMembers(number.toLong(), LocalContext.current).quantityString
    ) {}
    Slider(value = sliderPosition, onValueChange = {
        sliderPosition = it
        number = (it).toInt()
    }, valueRange = initValue.toFloat()..100f * initValue)

}

@Composable
fun ButtonsGroup() {
    val interactionSourcePrimary = remember { MutableInteractionSource() }
    val interactionSourceSecondary = remember { MutableInteractionSource() }
    val interactionSourceGhost = remember { MutableInteractionSource() }
    val coroutine = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        coroutine.launch {
            interactionSourcePrimary.emit(HoverInteraction.Enter())
            interactionSourceSecondary.emit(HoverInteraction.Enter())
            interactionSourceGhost.emit(HoverInteraction.Enter())
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        PrimaryButton(modifier = Modifier.weight(8f)) {}
        Spacer(modifier = Modifier.weight(1f))
        SecondaryButton(modifier = Modifier.weight(8f)) {}
        Spacer(modifier = Modifier.weight(1f))
        GhostButton(modifier = Modifier.weight(8f)) {}
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        PrimaryButton(
            modifier = Modifier.weight(8f),
            interactionSource = interactionSourcePrimary
        ) {}
        Spacer(modifier = Modifier.weight(1f))
        SecondaryButton(
            modifier = Modifier.weight(8f),
            interactionSource = interactionSourceSecondary
        ) {}
        Spacer(modifier = Modifier.weight(1f))
        GhostButton(modifier = Modifier.weight(8f), interactionSource = interactionSourceGhost) {}
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        PrimaryButton(modifier = Modifier.weight(8f), enabled = false) {}
        Spacer(modifier = Modifier.weight(1f))
        SecondaryButton(modifier = Modifier.weight(8f), enabled = false) {}
        Spacer(modifier = Modifier.weight(1f))
        GhostButton(modifier = Modifier.weight(8f), enabled = false) {}
    }
}

@Composable
fun TypographyGroup() {
    val textSample = stringResource(id = R.string.text_sample)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        TitleBlock(
            heading = stringResource(id = R.string.heading1_heading),
            description = stringResource(id = R.string.heading1_description)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = textSample, style = MyTypography.heading1)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        TitleBlock(
            heading = stringResource(id = R.string.heading2_heading),
            description = stringResource(id = R.string.heading2_description)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = textSample, style = MyTypography.heading2)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        TitleBlock(
            heading = stringResource(id = R.string.subheading1_heading),
            description = stringResource(id = R.string.subheading1_description)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = textSample, style = MyTypography.subheading1)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        TitleBlock(
            heading = stringResource(id = R.string.subheading2_heading),
            description = stringResource(id = R.string.subheading2_description)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = textSample, style = MyTypography.subheading2)

    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        TitleBlock(
            heading = stringResource(id = R.string.body1_heading),
            description = stringResource(id = R.string.body1_description)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = textSample, style = MyTypography.bodyText1)

    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        TitleBlock(
            heading = stringResource(id = R.string.body2_heading),
            description = stringResource(id = R.string.body2_description)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = textSample, style = MyTypography.bodyText2)

    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        TitleBlock(
            heading = stringResource(id = R.string.meta1_heading),
            description = stringResource(id = R.string.meta1_description)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = textSample, style = MyTypography.metadata1)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        TitleBlock(
            heading = stringResource(id = R.string.meta2_heading),
            description = stringResource(id = R.string.meta2_description)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = textSample, style = MyTypography.metadata2)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        TitleBlock(
            heading = stringResource(id = R.string.meta3_heading),
            description = stringResource(id = R.string.meta3_description)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = textSample, style = MyTypography.metadata3)
    }
}

@Composable
fun UserAvatarGroup() {
    var userAvatarState by remember { mutableStateOf(UserAvatarState.Default as UserAvatarState) }
    var checked by remember {
        mutableStateOf(userAvatarState == UserAvatarState.Edit)
    }
    val view = LocalView.current
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Edit mode", style = DevMeetTheme.typography.bodyText2)
        Switch(colors = SwitchDefaults.colors().copy(
            checkedBorderColor = DevMeetTheme.colorScheme.brandDefault,
            uncheckedBorderColor = DevMeetTheme.colorScheme.brandDefault,
            uncheckedThumbColor = DevMeetTheme.colorScheme.brandDefault
        ),
            checked = checked,
            onCheckedChange = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                if (it) {
                    userAvatarState = UserAvatarState.Edit
                    checked = true
                } else {
                    userAvatarState = UserAvatarState.Default
                    checked = false
                }
            })
    }
    UserAvatar(state = userAvatarState) {}

}

@Preview
@Composable
fun MainScreenPreview() {
    ElementsScreen1()
}