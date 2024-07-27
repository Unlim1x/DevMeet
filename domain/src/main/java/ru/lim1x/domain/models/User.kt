package ru.lim1x.domain.models

data class User(
    val id:Int,
    val name: String,
    val surname:String = "",
    val phone: String,
    val avatarURL: String,
    val hasAvatar: Boolean
)
