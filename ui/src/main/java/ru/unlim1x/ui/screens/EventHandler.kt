package ru.unlim1x.ui.screens

internal interface EventHandler<T> {
    fun obtain(event: T)
}