package ru.unlim1x.wb_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ru.unlim1x.wb_project.ui.screens.NavigationScreen
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavigationScreen()

                }
            }
        }
    }
}
