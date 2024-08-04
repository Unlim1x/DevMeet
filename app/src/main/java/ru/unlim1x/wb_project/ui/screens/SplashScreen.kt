package ru.unlim1x.wb_project.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import ru.unlim1x.wb_project.ui.navigation.MainNavGraphNodes
import ru.unlim1x.wb_project.ui.navigation.NavGraphNodes
import ru.unlim1x.wb_project.ui.viewmodels.splash_screen.SplashScreenEvent
import ru.unlim1x.wb_project.ui.viewmodels.splash_screen.SplashScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.splash_screen.SplashScreenViewState

private const val LOTTIE_ANIMATION_URL =
    "https://lottie.host/0d4bc3df-0f73-490f-908b-0f01b991cfa7/jC1TQrIOfB.json"


@Composable
fun SplashScreen(navController:NavController, viewModel:SplashScreenViewModel = koinViewModel()) {
    Log.e("SPlashScreen", "Recomposition")
    val viewState = viewModel.viewState().collectAsStateWithLifecycle()
    when(val state = viewState.value){

        SplashScreenViewState.Display -> {
            LottieAnimation()}
        is SplashScreenViewState.Finished -> {
            when(state.isAuthorized){
                true->{
                    LaunchedEffect(key1 = Unit) {
                        navController.navigate(MainNavGraphNodes.MainScreen.route) {
                            popUpTo(MainNavGraphNodes.SplashScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
                false->{
                    LaunchedEffect(key1 = Unit) {
                        navController.navigate(MainNavGraphNodes.AuthScreen.route){
                            popUpTo(MainNavGraphNodes.SplashScreen.route){
                                inclusive = true
                            }
                        }
                    }
                }
            }

        }
        SplashScreenViewState.Init -> {}
        else-> throw NotImplementedError("Unexpected state")
    }

   LaunchedEffect(key1 = viewState) {
       viewModel.obtain(SplashScreenEvent.OpenApp)
   }


}


@Composable
private fun LottieAnimation(modifier: Modifier = Modifier){
    val composition by rememberLottieComposition(LottieCompositionSpec.Url(LOTTIE_ANIMATION_URL))
    Box(modifier = modifier
        .fillMaxSize()
        .background(Color(0xFFf2f7fc)), contentAlignment = Alignment.Center) {
        LottieAnimation(composition)
    }
}