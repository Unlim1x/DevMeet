package ru.unlim1x.wb_project.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import android.Manifest
import ru.unlim1x.wb_project.ui.navigation.NavigationScreen
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import java.util.Locale
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat


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
            DevMeetTheme {
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
