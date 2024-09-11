package ru.lim1x.domain.models

data class Person(
    val id: Int,
    val name: String,
    val surname: String = "",
    val mainTag: String? = null,
    val avatarURL: String?,
)