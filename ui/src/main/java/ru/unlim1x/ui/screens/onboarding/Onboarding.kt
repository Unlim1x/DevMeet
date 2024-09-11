package ru.unlim1x.ui.screens.onboarding

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.ui.R
import ru.unlim1x.ui.kit.button.DisabledButton
import ru.unlim1x.ui.kit.button.PrimaryButton
import ru.unlim1x.ui.kit.tag.TagUi
import ru.unlim1x.ui.kit.tag.TagBig
import ru.unlim1x.ui.navigation.start_app.StartAppNavGraphNodes

private const val TOP_PADDING = 20
private const val HORIZONTAL_PADDING = 16
private const val BOTTOM_PADDING = 28
private const val GAP = 16
private const val HEADER_PADDING = 12
private const val TAG_FLOW_TOP_PADDING = 24

@Composable
internal fun Onboarding(navController: NavController) {
    val viewModel: OnboardingViewModel = koinViewModel()
    val state by viewModel.viewState().collectAsStateWithLifecycle()
    Log.e("State", "STATE IS $state")
    when (state) {
        OnboardingViewState.Loading -> Loading()
        is OnboardingViewState.Display -> {
            Body(isPrimaryButtonActive = (state as OnboardingViewState.Display).isButtonActive,
                tagUiList = (state as OnboardingViewState.Display).tagUiList,
                onTagClicked = {
                    viewModel.obtain(OnboardingEvent.OnTagClick(tagUi = it))
                },
                onPrimaryButtonClicked = {
                    viewModel.obtain(OnboardingEvent.OnMainButtonClick)
                    navController.navigate(StartAppNavGraphNodes.Main.route) {
                        popUpTo(StartAppNavGraphNodes.Onboarding.route) {
                            inclusive = true
                        }
                    }
                }, onSkipClicked = {

                })
        }
    }

}

@Composable
private fun Loading() {

}

@Composable
private fun Body(
    isPrimaryButtonActive: Boolean,
    tagUiList: List<TagUi>,
    onTagClicked: (tagUi: TagUi) -> Unit,
    onPrimaryButtonClicked: () -> Unit,
    onSkipClicked: () -> Unit
) {
    var buttonPartHeight by remember {
        mutableStateOf(0)
    }
    val density = LocalDensity.current.density
    val treshold = -5
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = HORIZONTAL_PADDING.dp, top = TOP_PADDING.dp, end = HORIZONTAL_PADDING.dp,
                bottom = BOTTOM_PADDING.dp
            )
    ) {
        MainPart(
            Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .padding(bottom = buttonPartHeight.dp), tagUiList = tagUiList
        ) {
            onTagClicked(it)
        }

        ButtonPart(modifier = Modifier
            .align(Alignment.BottomCenter)
            .onGloballyPositioned {
                buttonPartHeight =
                    (it.size.height / density + treshold).toInt()
            },
            isPrimaryButtonActive = isPrimaryButtonActive,
            onPrimaryButtonClicked = { onPrimaryButtonClicked() }) {
            onSkipClicked()
        }
    }
}

@Composable
private fun ButtonPart(
    modifier: Modifier,
    isPrimaryButtonActive: Boolean,
    onPrimaryButtonClicked: () -> Unit,
    onSkipClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (isPrimaryButtonActive) {
            true -> PrimaryButton(
                text = R.string.save,
                modifier = Modifier.padding(bottom = GAP.dp)
            ) {
                onPrimaryButtonClicked()
            }

            false -> DisabledButton(
                text = R.string.save,
                modifier = Modifier.padding(bottom = GAP.dp)
            )
        }
        Text(text = stringResource(R.string.tell_later),
            style = DevMeetTheme.newTypography.primary,
            color =
            DevMeetTheme.colorScheme.secondary,
            modifier = Modifier.clickable { onSkipClicked() })
    }
}

@Composable
private fun MainPart(
    modifier: Modifier,
    tagUiList: List<TagUi>,
    onTagClicked: (tagUi: TagUi) -> Unit
) {
    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = stringResource(R.string.interests),
                style = DevMeetTheme.newTypography.huge,
                modifier = Modifier.padding(bottom = HEADER_PADDING.dp)
            )
        }
        item {
            Text(
                text = stringResource(R.string.choose_interests), style =
                DevMeetTheme.newTypography.regular, color = DevMeetTheme.colorScheme.black
            )
        }
        item {
            TagPart(
                modifier = Modifier.padding(top = TAG_FLOW_TOP_PADDING.dp),
                tagUiList = tagUiList
            ) {
                onTagClicked(it)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TagPart(
    modifier: Modifier,
    tagUiList: List<TagUi>,
    onTagClicked: (tagUi: TagUi) -> Unit
) {
    FlowRow(modifier = modifier) {
        tagUiList.forEach {
            TagBig(text = it.text, modifier = Modifier.padding(4.dp), selected = it.isSelected) {
                onTagClicked(it)
            }
        }
    }
}

@Composable
@Preview
private fun SHow() {
    val testTagUi = TagUi(id = 1, text = "Дизайн", isSelected = false)
    val state = OnboardingViewState.Display(
        tagUiList = MutableList<TagUi>(40) { testTagUi },
        isButtonActive = false
    )
    Onboarding(navController = rememberNavController())
}