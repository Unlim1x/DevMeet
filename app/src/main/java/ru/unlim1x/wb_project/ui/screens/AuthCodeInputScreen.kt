package ru.unlim1x.wb_project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.navigation.AuthNavGraphNodes
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.buttons.GhostButton
import ru.unlim1x.wb_project.ui.uiKit.custominputview.PassCodeInput
import ru.unlim1x.wb_project.ui.uiKit.custominputview.model.PhoneNumberTransformation

private val FIGMA_MAIN_TEXT_HORIZONTAL_PADDING = 40.dp
private val FIGMA_MAIN_TEXT_TOP_PADDING = 169.dp
private val COLUMN_HORIZONTAL_PADDING = 24.dp
private val BOTTOM_PADDING = 48.dp
private val MAIN_TEXT_HORIZONTAL_PADDING =
    FIGMA_MAIN_TEXT_HORIZONTAL_PADDING - COLUMN_HORIZONTAL_PADDING

private const val animationDuration = 300

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthCodeInputScreen(navController: NavController, phone: String, code: String) {

    val phoneNumberText = transformPhoneNumber(code, phone)
    var topBarAnimation by remember { mutableStateOf(false) }
    var buttonState by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(backIconIsVisible = true,
                backIconAction = {navController.navigateUp()}
            )
        }) {
        topBarAnimation = true

        LazyColumn(
            modifier = Modifier.padding(
                start = COLUMN_HORIZONTAL_PADDING, end = COLUMN_HORIZONTAL_PADDING
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            contentPadding = PaddingValues(
                top = it.calculateTopPadding() +
                        FIGMA_MAIN_TEXT_TOP_PADDING - it.calculateTopPadding()
            )
        ) {

            item {
                Text(
                    modifier = Modifier.padding(horizontal = MAIN_TEXT_HORIZONTAL_PADDING),
                    text = stringResource(R.string.enter_code),
                    style = DevMeetTheme.typography.heading2
                )
            }
            item {
                Spacer(modifier = Modifier.size(8.dp))
            }
            item {
                Text(
                    modifier = Modifier
                        .padding(horizontal = MAIN_TEXT_HORIZONTAL_PADDING)
                        .padding(bottom = BOTTOM_PADDING),
                    text = stringResource(R.string.we_sent_code_to_number) + phoneNumberText,
                    style = DevMeetTheme.typography.bodyText2,
                    textAlign = TextAlign.Center,
                )

            }

            item {
                PassCodeInput(
                    modifier = Modifier
                        .padding(bottom = BOTTOM_PADDING)
                        .focusRequester(focusRequester)
                ) { code ->
                    coroutineScope.launch {
                        if (validateCode(code)) {
                            navController.navigate(route = AuthNavGraphNodes.ProfileNode.route){
                                popUpTo(route = AuthNavGraphNodes.PhoneNode.route)
                            }
                        }
                    }
                }
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            }


            item {
                GhostButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonText = stringResource(R.string.request_code_again),
                    enabled = buttonState
                ) {

                }
            }


        }

    }

}

fun validateCode(code: String): Boolean {
    return code.length == 4
}

fun transformPhoneNumber(code: String, phone: String): String {
    return code + " " +
            PhoneNumberTransformation().filter(AnnotatedString(phone, spanStyle = SpanStyle())).text
}

@Preview
@Composable
fun ShowCodeInputScreen() {
    AuthCodeInputScreen(rememberNavController(), phone = "9999999999", code = "+7")
}
