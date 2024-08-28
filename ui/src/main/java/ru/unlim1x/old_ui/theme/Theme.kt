package ru.unlim1x.old_ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import ru.unlim1x.old_ui.uiKit.theme.MyTypographyInter
import ru.unlim1x.old_ui.uiKit.theme.MyTypographySF
import ru.unlim1x.old_ui.uiKit.theme.Pink40
import ru.unlim1x.old_ui.uiKit.theme.Pink80
import ru.unlim1x.old_ui.uiKit.theme.PurpleGrey40
import ru.unlim1x.old_ui.uiKit.theme.PurpleGrey80
import ru.unlim1x.old_ui.uiKit.theme.myColorScheme


private val DarkColorScheme = darkColorScheme(
    primary = myColorScheme.brandDarkMode,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = myColorScheme.neutralWhite
)

private val LightColorScheme = lightColorScheme(
    primary = myColorScheme.brandDefault,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = myColorScheme.neutralWhite

)

@Composable
fun DevMeetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme

        }
    }



    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )

}

object DevMeetTheme {
    val colorScheme get() = myColorScheme

    @Deprecated(
        "Deprecated since new design was introduced",
        replaceWith = ReplaceWith("DevMeetTheme.newTypography")
    )
    val typography get() = MyTypographySF
    val newTypography get() = MyTypographyInter
}
