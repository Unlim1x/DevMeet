package ru.unlim1x.wb_project.ui.screens

internal interface EventHandler<T> {
    fun obtain(event: T)
}