package ru.unlim1x.wb_project.ui.viewmodels

interface EventHandler<T> {
    fun obtain(event:T)
}