package ru.andreysozonov.model.data.data

sealed class AppState {

    data class Success(val data: List<SearchResult>?): AppState()
    data class Error(val error: Throwable): AppState()
    data class Loading(val progress: Int?): AppState()
}