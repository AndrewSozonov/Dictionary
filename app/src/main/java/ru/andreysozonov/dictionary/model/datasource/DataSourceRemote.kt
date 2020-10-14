package ru.andreysozonov.dictionary.model.datasource

import io.reactivex.Observable
import ru.andreysozonov.dictionary.model.data.SearchResult

class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<SearchResult>> {
    override suspend fun getData(word: String): List<SearchResult> =
        remoteProvider.getData(word)

}