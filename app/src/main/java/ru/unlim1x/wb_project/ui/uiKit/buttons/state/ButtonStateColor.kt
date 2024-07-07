package ru.unlim1x.wb_project.ui.uiKit.buttons.state

import androidx.compose.ui.graphics.Color

sealed class ButtonStateColor(val color: Color) {
    class Initial : ButtonStateColor(Color(0xFF9A41FE))
    class Hovered : ButtonStateColor(Color(0xFF660EC8))
    class Disabled : ButtonStateColor(Color(0x7F9A41FE))
}