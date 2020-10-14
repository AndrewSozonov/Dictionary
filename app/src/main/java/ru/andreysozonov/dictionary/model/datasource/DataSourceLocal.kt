package ru.andreysozonov.dictionary.model.datasource

import io.reactivex.Observable
import ru.andreysozonov.dictionary.model.data.SearchResult

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<SearchResult>> {
    override suspend fun getData(word: String): List<SearchResult> =
        remoteProvider.getData(word)

}