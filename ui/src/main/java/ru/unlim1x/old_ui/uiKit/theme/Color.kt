package ru.unlim1x.old_ui.uiKit.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val DefaultPurple = Color(0xFF9A41FE)
val DarkPurple = Color(0xFF660EC8)

data class MyColors(
    val black: Color,
    /**Use for onPressed */
    val brandDark: Color,
    /**Use for button */
    val brandDefault: Color,
    /**Use for DarkMode */
    val brandDarkMode: Color,

    val brandLightVariant: Color,

    /**Use for backGround */
    val brandBackground: Color,
    /**Use for font */
    val neutralActive: Color,
    /**Use for DarkMode */
    val neutralDark: Color,
    /**Use for text */
    val neutralBody: Color,
    /**Use for secondary text */
    val neutralWeak: Color,
    /**Use for disabled */
    val neutralDisabled: Color,
    /**Use for divider */
    val neutralLine: Color,

    val neutralSecondaryBackground: Color,
    val neutralWhite: Color,
    /**Use for error */
    val accentDanger: Color,

    val accentWarning: Color,
    val accentSuccess: Color,

    val accentSafe: Color,

    val gradient01: List<Color>,
    val gradient02: List<Color>,

    val primary: Color,
    val disabled: Color,
    val disabledText: Color,
    val inputError: Color

    )

val myColorScheme = MyColors(
    black = Color(0xFF000000),
    brandDark = Color(0xFF660EC8),
    brandDefault = Color(0xFF9A41FE),
    brandDarkMode = Color(0xFF8207E8),
    brandLightVariant = Color(0xFFECDAFF),
    brandBackground = Color(0xFFF5ECFF),
    neutralActive = Color(0xFF29183B),
    neutralDark = Color(0xFF190E26),
    neutralBody = Color(0xFF1D0835),
    neutralWeak = Color(0xFFA4A4A4),
    neutralDisabled = Color(0xFFD4DBE7),
    neutralLine = Color(0xFFEDEDED),
    neutralSecondaryBackground = Color(0xFFF7F7FC),
    neutralWhite = Color(0xFFFFFFFF),
    accentDanger = Color(0xFFE94242),
    accentWarning = Color(0xFFFDCF41),
    accentSuccess = Color(0xFF2CC069),
    accentSafe = Color(0xFF7BCBCF),
    gradient01 = listOf(Color(0xFFD2D5F9), Color(0xFF2C37E1)),
    gradient02 = listOf(Color(0xFFEC9EFF), Color(0xFF5F2EEA)),
    primary = Color(0xFF9A10F0),
    disabled = Color(0xFFF6F6FA),
    disabledText = Color(0xFF9797AF),
    inputError = Color(0x19F0114C)
)