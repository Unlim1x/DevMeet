package ru.unlim1x.old_ui.uiKit.cards

import android.content.Context
import ru.unlim1x.ui.R

internal class QuantityMembers(var numberOfMembers: Long, var context: Context) {

    val quantityString get() = quantityToString()

    private fun quantityToString(): String {
        var temp = ""
        if (numberOfMembers >= 1000000)
            return "${numberOfMembers / 1000000},${numberOfMembers % 1000000 / 100000} млн. —  ${
                context.resources.getQuantityString(
                    R.plurals.people,
                    0
                )
            }"

        temp = "$numberOfMembers"
        if (temp.length > 4)
            temp = temp.replaceRange(temp.length - 3, temp.length - 3, " ")


        return "$temp  —  ${
            context.resources.getQuantityString(
                R.plurals.people,
                (numberOfMembers).toInt()
            )
        }"
    }
}