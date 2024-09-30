package ru.lim1x.domain.models

sealed class SearchRequest {
    data class Search(val text: String) : SearchRequest()
    data object SearchMore : SearchRequest()
}
