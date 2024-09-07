package ru.unlim1x.old_ui.uiKit.theme


import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.ui.R


val sfprofamily = FontFamily(
    Font(R.font.sfpro_display_regular, FontWeight.Normal),
    Font(R.font.sfpro_display_semibold, FontWeight.SemiBold),
    Font(R.font.sfpro_display_bold, FontWeight.Bold),
)


data class MyTypographySFTextStyles(
    val heading1: TextStyle,
    val heading2: TextStyle,
    val subheading1: TextStyle,
    val subheading2: TextStyle,
    val bodyText1: TextStyle,
    val bodyText2: TextStyle,
    val metadata1: TextStyle,
    val metadata2: TextStyle,
    val metadata3: TextStyle
)

val MyTypographySF = MyTypographySFTextStyles(
    heading1 = TextStyle(
        color = myColorScheme.neutralActive,
        fontFamily = sfprofamily, fontWeight = FontWeight.Bold, fontSize = 32.sp,
        lineHeight = 38.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    heading2 = TextStyle(
        color = myColorScheme.neutralActive,
        fontFamily = sfprofamily, fontWeight = FontWeight.Bold, fontSize = 24.sp,
        lineHeight = 28.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    subheading1 = TextStyle(
        color = myColorScheme.neutralActive,
        fontFamily = sfprofamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 30.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    subheading2 = TextStyle(
        color = myColorScheme.neutralActive,
        fontFamily = sfprofamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 28.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    bodyText1 = TextStyle(
        color = myColorScheme.neutralActive,
        fontFamily = sfprofamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    bodyText2 = TextStyle(
        color = myColorScheme.neutralActive,
        fontFamily = sfprofamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    metadata1 = TextStyle(
        color = myColorScheme.neutralWeak,
        fontFamily = sfprofamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    metadata2 = TextStyle(
        color = myColorScheme.neutralWeak,
        fontFamily = sfprofamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 16.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    metadata3 = TextStyle(
        color = myColorScheme.neutralWeak,
        fontFamily = sfprofamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        lineHeight = 16.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
)

val interfamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.inter, FontWeight.Black, FontStyle.Normal),
    Font(R.font.inter_blackitalic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.inter_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.inter_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.inter_thin, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.inter_thinitalic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.inter_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.inter_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.inter_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.inter_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.inter_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.inter_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.inter_extralight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.inter_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
)

data class MyTypographyINTERTextStyles(
    val h1: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val primary: TextStyle,
    val secondary: TextStyle,
    val regular: TextStyle,
    val medium: TextStyle,
    val big: TextStyle,
    val huge: TextStyle,
)

val MyTypographyInter = MyTypographyINTERTextStyles(
    huge = TextStyle(
        color = DevMeetTheme.colorScheme.black,
        fontFamily = interfamily, fontWeight = FontWeight.SemiBold, fontSize = 48.sp,
        lineHeight = 48.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    h1 = TextStyle(
        color = DevMeetTheme.colorScheme.black,
        fontFamily = interfamily, fontWeight = FontWeight.Bold, fontSize = 34.sp,
        lineHeight = 34.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    h3 = TextStyle(
        color = DevMeetTheme.colorScheme.neutralWhite,
        fontFamily = interfamily, fontWeight = FontWeight.SemiBold, fontSize = 18.sp,
        lineHeight = 22.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    h4 = TextStyle(
        color = DevMeetTheme.colorScheme.neutralActive,
        fontFamily = interfamily, fontWeight = FontWeight.SemiBold, fontSize = 14.sp,
        lineHeight = 16.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    primary = TextStyle(
        color = DevMeetTheme.colorScheme.primary,
        fontFamily = interfamily, fontWeight = FontWeight.Medium, fontSize = 18.sp,
        lineHeight = 22.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    secondary = TextStyle(
        color = DevMeetTheme.colorScheme.secondary,
        fontFamily = interfamily, fontWeight = FontWeight.Medium, fontSize = 14.sp,
        lineHeight = 16.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    regular = TextStyle(
        color = DevMeetTheme.colorScheme.neutralActive,
        fontFamily = interfamily, fontWeight = FontWeight.Normal, fontSize = 19.sp,
        lineHeight = 22.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    medium = TextStyle(
        color = DevMeetTheme.colorScheme.neutralActive,
        fontFamily = interfamily, fontWeight = FontWeight.Medium, fontSize = 16.sp,
        lineHeight = 20.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    big = TextStyle(
        color = DevMeetTheme.colorScheme.neutralActive,
        fontFamily = interfamily, fontWeight = FontWeight.Medium, fontSize = 22.sp,
        lineHeight = 26.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
)