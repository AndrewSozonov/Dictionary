package ru.andreysozonov.repository.datasource


import ru.andreysozonov.model.data.data.AppState
import ru.andreysozonov.model.data.data.SearchResult

interface DataSourceLocal<T> :
    DataSource<T> {
    suspend fun saveToDB(appState: ru.andreysozonov.model.data.data.AppState)

}