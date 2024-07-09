package ru.unlim1x.wb_project.ui.uiKit.cards.model

import ru.unlim1x.wb_project.ui.uiKit.cards.TimeAndPlace

data class Event(
    val name: String,
    val timeAndPlace: TimeAndPlace,
    val isFinished: Boolean,
    val tags: List<String>,
    val description:String = LoremIpsum.Short.text,
    val id :Int = 0
)
