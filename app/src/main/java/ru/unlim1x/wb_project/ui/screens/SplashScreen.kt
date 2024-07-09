package ru.unlim1x.wb_project.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

private const val LOTTIE_ANIMATION_URL =
    "https://lottie.host/0d4bc3df-0f73-490f-908b-0f01b991cfa7/jC1TQrIOfB.json"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashScreen() {


    val composition by rememberLottieComposition(LottieCompositionSpec.Url(LOTTIE_ANIMATION_URL))
    LaunchedEffect(key1 = Unit) {
        delay(4000)

    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFf2f7fc)), contentAlignment = Alignment.Center) {
        LottieAnimation(composition)
    }
}