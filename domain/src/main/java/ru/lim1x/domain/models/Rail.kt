package ru.lim1x.domain.models

enum class RailType {
    Community,
    Banner,
    Person,
    Nothing
}

data class Rail(
    val railType: RailType,
    val content: Any
)
