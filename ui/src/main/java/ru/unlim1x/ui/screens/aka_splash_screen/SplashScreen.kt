package ru.unlim1x.ui.screens.aka_splash_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.unlim1x.ui.R
import ru.unlim1x.ui.navigation.start_app.StartAppNavGraphNodes

private const val ROTATION_ANIM_DURATION = 500
private const val AFTER_ROTATION_ANIM_DELAY = 300L
private const val SCALE_ANIM_DURATION = 500
private const val AFTER_SCALE_ANIM_DELAY = 500L
private const val DELAY_BEFORE_REQUEST_ACCESS = 500L

@Composable
internal fun SplashScreen(navController: NavController) {
    val viewModel: SplashScreenViewModel = koinViewModel()
    val permissionState by viewModel.locationPermissionState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        viewModel.onPermissionRequested(isGranted)
    }

    var rotation by remember { mutableFloatStateOf(0f) }
    var scale by remember { mutableFloatStateOf(1f) }


    val rotationAnimation by animateFloatAsState(
        targetValue = rotation,
        animationSpec = tween(durationMillis = ROTATION_ANIM_DURATION, easing = LinearEasing)
    )

    val scaleAnimation by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(durationMillis = SCALE_ANIM_DURATION, easing = FastOutSlowInEasing)
    )


    LaunchedEffect(Unit) {
        rotation = 360f
        delay(AFTER_ROTATION_ANIM_DELAY)
        scale = 1.2f
        delay(AFTER_SCALE_ANIM_DELAY)
        scale = 1f
        delay(DELAY_BEFORE_REQUEST_ACCESS)
        scope.launch { locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION) }
    }



    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_splash), contentDescription = "",
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds
        )
        Image(
            painter = painterResource(id = R.drawable.logo_splash), contentDescription = "",
            modifier = Modifier
                .padding(24.dp)
                .graphicsLayer(
                    rotationZ = rotationAnimation,
                    scaleX = scaleAnimation,
                    scaleY = scaleAnimation
                )
        )

    }

    when (permissionState) {
        true -> {
            LaunchedEffect(key1 = Unit) {
                delay(DELAY_BEFORE_REQUEST_ACCESS)
                navController.navigate(StartAppNavGraphNodes.Onboarding.route) {
                    popUpTo(StartAppNavGraphNodes.Splash.route) {
                        inclusive = true
                    }
                }
            }
        }

        false -> {
            LaunchedEffect(key1 = Unit) {
                delay(DELAY_BEFORE_REQUEST_ACCESS)
                navController.navigate(StartAppNavGraphNodes.Onboarding.route) {
                    popUpTo(StartAppNavGraphNodes.Splash.route) {
                        inclusive = true
                    }
                }
            }
        }

        null -> {}
    }
}

@Composable
@Preview
private fun Show() {
    SplashScreen(rememberNavController())
}