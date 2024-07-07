package ru.unlim1x.wb_project.ui.uiKit.theme


import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.unlim1x.wb_project.ui.theme.Wb_projectTheme

object PrimaryColorRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor(): Color = Wb_projectTheme.colorScheme.brandDefault

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0f, 0f, 0f, 0.1f)


}

object NoRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor(): Color = Color.White

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0f, 0f, 0f, 0f)


}