package ru.lim1x.domain.models

data class AuthorizationResult(
    val isSuccessful : Boolean,
    val userId: Int
)