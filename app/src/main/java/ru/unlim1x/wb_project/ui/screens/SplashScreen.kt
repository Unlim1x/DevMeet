package ru.unlim1x.wb_project.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dotlottie.dlplayer.Mode
import com.lottiefiles.dotlottie.core.compose.runtime.DotLottieController
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashScreen() {



    LaunchedEffect(key1 = Unit) {
        delay(4000)

    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        DotLottieAnimation(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFf2f7fc)),
            source = DotLottieSource.Url("https://lottie.host/0d4bc3df-0f73-490f-908b-0f01b991cfa7/jC1TQrIOfB.json"),
            autoplay = true,
            loop = true,
            speed = 1f,
            useFrameInterpolation = false,
            playMode = Mode.FORWARD
        )
    }


}