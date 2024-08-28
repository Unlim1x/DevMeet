package ru.unlim1x.old_ui.screens

internal interface EventHandler<T> {
    fun obtain(event: T)
}