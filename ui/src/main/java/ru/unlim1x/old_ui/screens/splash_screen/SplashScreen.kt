package ru.unlim1x.old_ui.screens.splash_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import org.koin.androidx.compose.koinViewModel
import ru.unlim1x.old_ui.navigation.nav_graph.global.MainNavGraphNodes
import ru.unlim1x.ui.R


@Composable
internal fun SplashScreen(
    navController: NavController,
    viewModel: SplashScreenViewModel = koinViewModel()
) {
    val viewState = viewModel.viewState().collectAsStateWithLifecycle()
    when (val state = viewState.value) {

        SplashScreenViewState.Display -> {
            LottieAnimation()
        }

        is SplashScreenViewState.Finished -> {
            when (state.isAuthorized) {
                true -> {
                    LaunchedEffect(key1 = Unit) {
                        navController.navigate(MainNavGraphNodes.MainScreen.route) {
                            popUpTo(MainNavGraphNodes.SplashScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }

                false -> {
                    LaunchedEffect(key1 = Unit) {
                        navController.navigate(MainNavGraphNodes.AuthScreen.route) {
                            popUpTo(MainNavGraphNodes.SplashScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }

        }

        SplashScreenViewState.Init -> {}
        else -> throw NotImplementedError("Unexpected state")
    }

    LaunchedEffect(key1 = viewState) {
        viewModel.obtain(SplashScreenEvent.OpenApp)
    }


}


@Composable
private fun LottieAnimation(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_splash_animation))
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFf2f7fc)), contentAlignment = Alignment.Center
    ) {
        LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever)
    }
}