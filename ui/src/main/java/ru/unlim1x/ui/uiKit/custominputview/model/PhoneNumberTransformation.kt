package ru.unlim1x.ui.uiKit.custominputview.model

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

internal class PhoneNumberTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {

        var out = ""
        text.forEachIndexed { index, _ ->
            out += text[index]
            when (index) {
                2 -> out += " "
                5 -> out += "-"
                7 -> out += "-"
            }
        }

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int) =
                when {
                    offset <= 2 -> offset
                    offset <= 5 -> offset + 1
                    offset <= 7 -> offset + 2
                    offset <= 9 -> offset + 3
                    else -> 13
                }

            override fun transformedToOriginal(offset: Int) =
                when {
                    offset <= 2 -> offset
                    offset <= 6 -> offset - 1
                    offset <= 9 -> offset - 2
                    offset <= 12 -> offset - 3
                    else -> 10
                }

        }
        return TransformedText(
            text = AnnotatedString(text = out),
            offsetMapping = numberOffsetTranslator
        )
    }
}