package ru.andreysozonov.dictionary.model.datasource

import io.reactivex.Observable
import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.model.data.SearchResult

interface DataSourceLocal<T> :
    DataSource<T> {
    suspend fun saveToDB(appState: AppState)

}