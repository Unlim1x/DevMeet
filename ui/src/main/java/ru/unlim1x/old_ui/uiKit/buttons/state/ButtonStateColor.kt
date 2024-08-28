package ru.unlim1x.old_ui.uiKit.buttons.state

import androidx.compose.ui.graphics.Color

internal sealed class ButtonStateColor(val color: Color) {
    class Initial : ButtonStateColor(Color(0xFF9A41FE))
    class Hovered : ButtonStateColor(Color(0xFF660EC8))
    class Disabled : ButtonStateColor(Color(0x7F9A41FE))
}