package ru.andreysozonov.repository.datasource


import ru.andreysozonov.model.data.data.SearchResult

class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<ru.andreysozonov.model.data.data.SearchResult>> {
    override suspend fun getData(word: String): List<ru.andreysozonov.model.data.data.SearchResult> =
        remoteProvider.getData(word)

}