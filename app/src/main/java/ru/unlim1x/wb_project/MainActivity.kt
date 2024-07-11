package ru.unlim1x.wb_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import ru.unlim1x.wb_project.ui.screens.AuthentificationScreen
import ru.unlim1x.wb_project.ui.screens.NavigationScreen
import ru.unlim1x.wb_project.ui.screens.SplashScreen
import ru.unlim1x.wb_project.ui.theme.Wb_projectTheme
import java.util.Locale


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = resources.configuration
        val lang = "ru"
        val locale = Locale(lang)
        Locale.setDefault(locale)
        config.setLocale(locale)


        createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)

        setContent {
            Wb_projectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val loaded = remember { mutableStateOf(false) }

                    LaunchedEffect(key1 = Unit) {
                        delay(4000)
                        loaded.value = true

                    }
                    if (!loaded.value)
                        SplashScreen()
                    else {
                        AuthentificationScreen()
                    }

                }
            }
        }
    }
}
