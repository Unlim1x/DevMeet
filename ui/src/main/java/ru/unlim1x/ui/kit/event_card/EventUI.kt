package ru.unlim1x.ui.kit.event_card

data class EventUI(
    val id: Int,
    val name: String,
    val imageUri: Any,
    val date: String,
    val address: String,
    val tags: List<String>
)
